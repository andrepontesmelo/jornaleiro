require 'fileutils'
require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative '../capybara_util'
require_relative '../journal'
require_relative '../meta_documents'

module Jornaleiro

  class JournalMG < Journal

    include Capybara::DSL

    LINKS_FILE = 'pdfs.txt'
    JOURNAL_SHORT_NAME = 'mg'

    # Title for each session
    TITLE_NOTICIARIO = 'Noticiário'
    TITLE_TERCEIROS = 'Publicações de Terceiros'
    TITLE_EXTRA = 'Edição Extra'
    TITLE_LEG = 'Diário do Leg'
    TITLE_JUS = 'Diário da Jus'
    TITLE_EXEC = 'Diário do Exec'
    TITLE_ANEXO = 'Anexo'

    # Database id for each session
    SESSION_NOTICIARIO = 1
    SESSION_TERCEIROS = 3
    SESSION_EXTRA = 13
    SESSION_LEG = 10
    SESSION_JUS = 11
    SESSION_EXEC = 2
    SESSION_ANEXO = 12

    HOST = 'http://jornal.iof.mg.gov.br/jspui/UltimoJornal'
    
    def initialize
      super()

      @recovery = {}
      @docs = MetaDocuments.new
      @tmp_path = "/tmp/#{JOURNAL_SHORT_NAME}"

      @months = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
                 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro']
    end

    def get_journal_id
      1
    end

    def get_initial_date
      Date.parse('2005-07-01')
    end

    def valid_date?(data)
      (!data.sunday? && !data.monday?)
    end

    def fix_encoding(string)
      URI.unescape(string).force_encoding("UTF-8")
    end

    def hash(sessao_id, page)
      "#{sessao_id}_#{page}"
    end

    def get_session(session_title, session_id)
      meta = MetaDocument.new(session_id)

      opened_window = window_opened_by do
        click_on(session_title)
      end

      within_window opened_window do
        sleep 5
        page_count = find('#id-div-numero-pagina-corrente').text.split(' ').last.to_i
        if (page_count == 0 || page_count == 200 || session_id == SESSION_ANEXO)
          page_count = 1
        end

        puts "Session #{session_title} has #{page_count} page(s)."

        for p in 1..page_count
          sleep 2
          recovered = @recovery[hash(session_id, p)]
          if (recovered.nil?)

            print (p % 10 == 0 ? (p/10) : ".")

            fill_in 'input-pagina-destino', :with => p.to_s
            sleep 1
            click_on('ok')
            sleep 1

            arquivo_pdf = find('#framePdf')[:src]
            arquivo_pdf = fix_encoding(arquivo_pdf)
            arquivo_pdf = arquivo_pdf.split('?')[0]

            recovered = [arquivo_pdf, arquivo_pdf.split('/').last.split('_').first]
            @recovery[hash(session_id, p)] = recovered;
          else
            puts "Recovered: page #{p} of session #{session_id}. "
          end

          meta.links.push(recovered[0])
          meta.session = recovered[1]
        end

        page.execute_script 'window.close();'
        meta.page_count = page_count
      end

      meta

    rescue Capybara::ElementNotFound => e
      print "\nSkipping '#{session_title}' - #{e.message} "

      if page.windows.count > 1
        within_window opened_window do
          page.execute_script "window.close();"
        end
      end

      if (e.message.include? 'framePdf')
        raise StandardError
      else
        puts ' - Will not try again.'
        return meta;
      end
    end

    def fetch_meta_documents(day, month, year)

      page.reset_session!
      visit('http://jornal.iof.mg.gov.br/jspui/UltimoJornal')

      within('#links-constantes-direita') do
        click_on(year)
      end

      click_on(@months[month.to_i - 1])

      within('#id-lista-subcomunidades') do
        link_dia = page.find_link(day.to_s)
        link_dia.click
      end

      @docs.push(get_session(TITLE_TERCEIROS, SESSION_TERCEIROS)) unless @docs.has_session?(SESSION_TERCEIROS)

      @docs.push(get_session(TITLE_NOTICIARIO, SESSION_NOTICIARIO)) unless @docs.has_session?(SESSION_NOTICIARIO)

      @docs.push(get_session(TITLE_EXTRA, SESSION_EXTRA)) unless @docs.has_session?(SESSION_EXTRA)

      @docs.push(get_session(TITLE_LEG, SESSION_LEG)) unless @docs.has_session?(SESSION_LEG)

      @docs.push(get_session(TITLE_JUS, SESSION_JUS)) unless @docs.has_session?(SESSION_JUS)

      @docs.push(get_session(TITLE_EXEC, SESSION_EXEC)) unless @docs.has_session?(SESSION_EXEC)

      @docs.push(get_session(TITLE_ANEXO, SESSION_ANEXO)) unless @docs.has_session?(SESSION_ANEXO)

      @docs

    rescue Capybara::Poltergeist::TimeoutError, StandardError => e
      if (!e.message.eql?("Unable to find link \"#{day.to_s}\""))

        puts e.message
        sleep 10
        retry
      else
        puts "The is no public journal @ #{day.to_s}."
        return nil;
      end
    end

    def prepare
      super

      CapybaraUtil.new.set_driver :poltergeist
      Capybara.app_host = HOST
    end

    def download_pdfs
      Dir.chdir(@tmp_path) do

        puts 'downloading pdfs ..'
        command_line = "cat #{LINKS_FILE} | parallel  --gnu --no-notice 'wget -q {}'"
        puts command_line
        system(command_line)

        puts system('du -hs')

        puts 'Transcribing pdf to text '
        system('for file in *.pdf*; do pdftotext -raw "$file" "$file.transcrito"; done')

        puts "Removing common '. ' sequences "
        system('for file in *.transcrito; do sed -i -- "s/. . / /g" "$file"; done')

        puts 'Shrinking empty spaces and removing line breaks'
        system('for file in *.transcrito; do tr "\n" " " < "$file"  | tr -s " " >> "$file".limpo; done')

        puts 'Removing hyphenation'
        system('for file in *.limpo; do sed -i -- "s/- //g" "$file"; done')
      end
    end

    def save_links
      Dir.chdir(@tmp_path) do
        File.open(LINKS_FILE, 'w+') do |f|
          @docs.merge_links.each { |element| f.puts(element) }
        end
      end
    end

    def insert(documents, date)
      pgsql = PgSQL.new

      documents.get_documents.each { |document|

        if (!document.page_count.nil?)

          session_id = document.session_id
          session = document.session
          page_count = document.page_count

          puts "Inserting #{page_count} documents within session #{session}."

          for p in 1..page_count
            print '.'
            pgsql.insert_document(date, p, session_id, read_file(JOURNAL_SHORT_NAME, session, session_id, p))
          end

          puts ''
        end
      }

      pgsql.destroy()
    end


    def fetch_date(day, month, year, data)
      puts 'fetch_date MG'
      prepare

      @docs = MetaDocuments.new
      @recovery = {}
      documents = fetch_meta_documents(day, month, year)
      if (!documents.nil?)
        save_links

        download_pdfs() if (documents.merge_links.length > 0)

        insert(documents, data)
      end

    end

  rescue Capybara::ElementNotFound => e
    puts "Skipping date #{data} due to unrecovery error #{e}"
  end

end
