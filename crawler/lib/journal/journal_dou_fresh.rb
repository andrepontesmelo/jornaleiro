require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative 'journal_dou'
require_relative '../capybara_util'
require_relative '../bash_tools'

module Jornaleiro
  # Crawler script for recent DOU public journals
  class JournalDOUFresh < JournalDOU
    include Capybara::DSL

    DOU_BACKUP_PATH = '/home/dou'.freeze
    LONG_SLEEP = 10

    def initialize
      super()

      @tmp_path = '/tmp/dou'
      @bash_tools = BashTools.new(@tmp_path)
    end

    def prepare
      super

      CapybaraUtil.new.use_poltergeist
      Capybara.app_host = 'http://portal.in.gov.br'
    end

    def session_id(session_name)
      return 5 if session_name == 'DO1'
      return 6 if session_name == 'DO2'
      return 7 if session_name == 'DO3'
      return 14 if session_name == 'EDJF1'
      raise StandardError, "Session not expected: #{session_name}"
    end

    def clean_file_name(file_name)
      file_name[/\w+.pdf/]
    end

    def clean_file_names
      puts 'Step 3: Cleaning pdf file names.'

      Dir.chdir(@tmp_path) do
        files = Dir.entries('.')

        files.each do |f|
          cleaned = clean_file_name(f)
          File.rename(f, cleaned) unless cleaned.nil?
        end
      end
    end

    def initial_date
      Date.parse('2015-03-03')
    end

    def browse(day, month, year)
      visit '/#leitura_jornais'
      check 'chk_avancada_0'
      fill_in 'dt_inicio_leitura_jornais', with: "#{day}/#{month}"
      fill_in 'dt_fim_leitura_jornais', with: "#{day}/#{month}"

      select year.to_s, :from => 'ano_leitura_jornais'

      result_window = window_opened_by do
        click_on 'BUSCAR'
      end

      result_window
    end

    def write_pdf_links(urls, links_file)
      Dir.chdir(@tmp_path) do
        puts system('rm *.pdf*')

        File.open(links_file, 'w+') do |f|
          urls.each { |url| f.puts(url) }
        end
      end
    end

    def hrefs
      find('#ResultadoConsulta').all('a')
    end

    def discover_main_urls(urls)
      urls[:do1] = hrefs[5][:onclick].to_s[/(http.+')/].to_s.chomp("'")
      urls[:do2] = hrefs[11][:onclick].to_s[/(http.+')/].to_s.chomp("'")
      urls[:do3] = hrefs[17][:onclick].to_s[/(http.+')/].to_s.chomp("'")
    end

    def discover_secundary_urls(urls)
      if hrefs.length >= 23
        urls[:extra] = hrefs[23][:onclick].to_s[/(http.+')/].to_s.chomp("'")
      end
    end

    def discover_urls
      urls = {}

      discover_main_urls(urls)
      discover_secundary_urls(urls)

      urls
    end

    def download_urls
      urls = discover_urls
      page.execute_script 'window.close();'
      links_file = 'pdfs.links.txt'
      write_pdf_links(urls, links_file)
      @bash_tools.download_files_parallel(links_file)
      @bash_tools.download_file(urls[:extra]) unless urls[:extra].nil?
    end

    def download(day, month, year)
      puts 'Step 1: Fetching pdf urls.'
      page.reset_session!
      result_window = browse day, month, year

      within_window result_window do
        download_urls
      end

    rescue Capybara::Poltergeist::TimeoutError
      sleep LONG_SLEEP
      retry
    end

    def parse_page(slices)
      case slices[4]
      when 'Extra.pdf'
        return slices[5]
      when 'SuplementoAnvisa.pdf'
        return slices[5]
      when 'EDJF1'
        return slices[4]
      else
        return slices[4]
      end
    end

    def parse_session(slices)
      case slices[4]
      when 'Extra.pdf'
        8
      when 'SuplementoAnvisa.pdf'
        9
      when 'EDJF1'
        14
      else
        session_id(slices[0])
      end
    end

    def parse_file(file)
      attrs = {}

      slices = file.split('_')

      attrs[:year] = slices[1]
      attrs[:month] = slices[2]
      attrs[:day] = slices[3].split('.')[0]
      attrs[:page] = parse_page(slices)
      attrs[:session] = parse_session(slices)
      attrs[:page] = parse_page(slices)

      attrs
    end

    def insert_document(pgsql, file, date)
      content = File.read(file)
      pgsql.insert_document(date, attr[:page], attr[:session], content)
      print '.'
    end

    def insert_db(date)
      puts 'Step 5: INSERT into db'
      pgsql = PgSQL.new

      Dir.chdir(@tmp_path) do
        files = Dir.entries '.'
        files.each do |f|
          attr = (f.include?('.pdf') ? parse_file(f) : nil)
          insert_document(pgsql, f, date) unless attr.nil?
        end
      end

      pgsql.destroy
    end

    def fetch_date(day, month, year, date)
      prepare
      puts 'fetch_date DOU'
      download(day, month, year)
      clean_file_names
      @bash_tools.pdf_to_text_pages_pages
      insert_db(date)

    rescue Capybara::ElementNotFound => e
      puts "Skipping date #{date} due to unrecovery error #{e}."
    end
  end
end
