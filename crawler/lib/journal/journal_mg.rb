require 'fileutils'
require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative '../capybara_util'
require_relative '../journal'
require_relative '../meta_documents'
require_relative '../bash_tools'

module Jornaleiro
  # Crawler for Minas Gerais state level public journal.
  class JournalMG < Journal
    include Capybara::DSL
    LINKS_FILE = 'pdfs.txt'.freeze
    JOURNAL_SHORT_NAME = 'mg'.freeze

    # Title for each session
    TITLE_NOTICIARIO = 'Noticiário'.freeze
    TITLE_TERCEIROS = 'Publicações de Terceiros'.freeze
    TITLE_EXTRA = 'Edição Extra'.freeze
    TITLE_LEG = 'Diário do Leg'.freeze
    TITLE_JUS = 'Diário da Jus'.freeze
    TITLE_EXEC = 'Diário do Exec'.freeze
    TITLE_ANEXO = 'Anexo'.freeze

    # Database id for each session
    SESSION_NOTICIARIO = 1
    SESSION_TERCEIROS = 3
    SESSION_EXTRA = 13
    SESSION_LEG = 10
    SESSION_JUS = 11
    SESSION_EXEC = 2
    SESSION_ANEXO = 12

    HOST = 'http://jornal.iof.mg.gov.br/jspui/UltimoJornal'.freeze
    def initialize
      super()

      @recovery = {}
      @docs = MetaDocuments.new
      @tmp_path = "/tmp/#{JOURNAL_SHORT_NAME}"
      @bash_tools = BashTools.new(@tmp_path)

      @months = %w(Janeiro Fevereiro Março Abril Maio Junho Julho \\
                   Agosto Setembro Outubro Novembro Dezembro)
    end

    def journal_id
      1
    end

    def initial_date
      Date.parse('1996-10-04')
    end

    def valid_date?(data)
      (!data.sunday? && !data.monday?)
    end

    def fix_encoding(string)
      URI.unescape(string).force_encoding('UTF-8')
    end

    def hash(session_id, page)
      "#{session_id}_#{page}"
    end

    def fetch_page(session_id, p, meta)
      sleep 2
      recovered = @recovery[hash(session_id, p)]
      if recovered.nil?

        print(p % 10 == 0 ? (p / 10) : '.')

        fill_in 'input-pagina-destino', :with => p.to_s
        sleep 1
        click_on('ok')
        sleep 1

        file_pdf = find('#framePdf')[:src]
        file_pdf = fix_encoding(file_pdf)
        file_pdf = file_pdf.split('?')[0]

        recovered = [file_pdf, file_pdf.split('/').last.split('_').first]
        @recovery[hash(session_id, p)] = recovered
      else
        puts "Recovered: page #{p} of session #{session_id}. "
      end

      meta.links.push(recovered[0])
      meta.session = recovered[1]
    end

    def page_count(session_id)
      pages = find('#id-div-numero-pagina-corrente').text.split(' ').last.to_i
      pages = 1 if pages == 0 || pages == 200 || session_id == SESSION_ANEXO

      pages
    end

    def fetch_session(session_title, session_id)
      meta = MetaDocument.new(session_id)

      opened_window = window_opened_by do
        click_on(session_title)
      end

      puts "Session id is #{session_id}"
      within_window opened_window do
        sleep 5
        meta.page_count = page_count(session_id)
        puts "Session #{session_title} has #{meta.page_count} page(s)."

        for p in 1..meta.page_count
          fetch_page(session_id, p, meta)
        end

        page.execute_script 'window.close();'
      end

      meta

    rescue Capybara::ElementNotFound => e
      print "\nSkipping '#{session_title}' - #{e.message} "

      if page.windows.count > 1 
        within_window opened_window { close_window }
      end

      raise StandardError if e.message.include? 'framePdf'
      puts ' - Will not try again.'
      return meta
    end

    def close_window
      page.execute_script 'window.close();'
    end

    def fetch_sessions
      @docs.push(fetch_session(TITLE_TERCEIROS, SESSION_TERCEIROS)) unless
       @docs.session?(SESSION_TERCEIROS)

      @docs.push(fetch_session(TITLE_NOTICIARIO, SESSION_NOTICIARIO)) unless
       @docs.session?(SESSION_NOTICIARIO)

      @docs.push(fetch_session(TITLE_EXTRA, SESSION_EXTRA)) unless
       @docs.session?(SESSION_EXTRA)

      @docs.push(fetch_session(TITLE_LEG, SESSION_LEG)) unless
       @docs.session?(SESSION_LEG)

      @docs.push(fetch_session(TITLE_JUS, SESSION_JUS)) unless
       @docs.session?(SESSION_JUS)

      @docs.push(fetch_session(TITLE_EXEC, SESSION_EXEC)) unless
       @docs.session?(SESSION_EXEC)

      @docs.push(fetch_session(TITLE_ANEXO, SESSION_ANEXO)) unless
       @docs.session?(SESSION_ANEXO)
    end

    def fetch_meta_documents(day, month, year)
      page.reset_session!
      visit('http://jornal.iof.mg.gov.br/jspui/UltimoJornal')

      within('#links-constantes-direita') do
        click_on(year)
      end

      click_on(@months[month - 1])

      within('#id-lista-subcomunidades') do
        link_dia = page.find_link(day.to_s)
        link_dia.click
      end

      fetch_sessions

      @docs
    rescue Capybara::Poltergeist::TimeoutError, StandardError => e
      if e.message.eql?("Unable to find link \"#{day}\"")
        puts "The is no public journal @ #{day}."
        return nil
      else
        puts e.message
        sleep 10
        retry
      end
    end

    def prepare
      super

      CapybaraUtil.new.use_poltergeist
      Capybara.app_host = HOST
    end

    def save_links(file)
      Dir.chdir(@tmp_path) do
        File.open(file, 'w+') do |f|
          @docs.merge_links.each { |element| f.puts(element) }
        end
      end
    end

    def insert_document(document, date, pgsql)
      session_id = document.session_id
      session = document.session
      
      puts "Inserting #{document.page_count} documents within session #{session}."

      for p in 1..document.page_count do
        print '.'
        content = read_file(session, session_id, p)
        pgsql.insert_document(date, p, session_id, content)
      end
    end

    def insert(documents, date)
      pgsql = PgSQL.new
      documents.documents.each do |document|
        next if document.page_count.nil?
        insert_document(document, date, pgsql)
        puts
      end

      pgsql.destroy
    end

    def fetch_date(day, month, year, data)
      puts 'fetch_date MG'
      prepare

      @docs = MetaDocuments.new
      @recovery = {}
      documents = fetch_meta_documents(day, month, year)
      unless documents.nil?
        save_links(LINKS_FILE)

        @bash_tools.crawle_pdfs(LINKS_FILE) unless documents.merge_links.empty?

        insert(documents, data)
      end
    end

  rescue Capybara::ElementNotFound => e
    puts "Skipping date #{data} due to unrecoverable error #{e}"
  end
end
