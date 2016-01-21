require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative '../journal'
require_relative '../capybara_util'

module Jornaleiro

  class JornalDOU < Journal
    include Capybara::DSL

    DOU_BACKUP_PATH = "/home/dou"
    LONG_SLEEP = 10

    def initialize
      super()

      @tmp_path = "/tmp/dou"
    end

    def prepare
      super

      CapybaraUtil.new.set_driver :poltergeist
      Capybara.app_host = 'http://portal.in.gov.br'
    end

    def get_journal_id
      3
    end

    def get_session_id(session_name)
      case session_name
        when "DO1"
          5
        when "DO2"
          6
        when "DO3"
          7
        when "EDJF1"
          14
        else
          puts session_name
          raise StandardError
      end
    end

    def clean_file_name(file_name)
      file_name[/\w+.pdf/]
    end

    def clean_file_names()
      puts "Step 3: Cleaning pdf file names."

      Dir.chdir(@tmp_path) do

        files = Dir.entries(".")

        files.each { |f|
          cleaned = clean_file_name(f)
          File.rename(f, cleaned) unless cleaned.nil?
        }

      end
    end

    def get_initial_date
      Date.parse('2015-03-03')
    end

    def split_pages
      puts "Step 4: Splitting pdf pages"
      Dir.chdir(@tmp_path) do
        files = Dir.entries(".")

        files.each { |f|
          pdf = f[/\w+.pdf/]

          if (!pdf.nil?)
            saida = `pdfinfo #{pdf} | grep Pages:`
            page_count = saida.split(" ").last.to_i;

            puts "Transcribing pdf to txt,  cleaning  spaces, break lines and removing hyphenation"

            for p in 1..page_count
              print '.'
              pdf_page = "#{pdf}_#{p}"
              cleaned_pdf = "#{pdf_page}_limpo"

              system("pdftotext -raw -f #{p} -l #{p} #{pdf} #{pdf_page} ")
              system("tr '\n' ' ' < #{pdf_page} | tr -s ' ' >> #{cleaned_pdf}")
              system("sed -i -- 's/- //g' '#{cleaned_pdf}'")
              system("mv #{cleaned_pdf} #{pdf_page}")
            end

            puts "mv #{@tmp_path}/#{f} #{DOU_BACKUP_PATH}/#{f} "
            system("mv #{@tmp_path}/#{f} #{DOU_BACKUP_PATH}/#{f} ")
          end
        }
      end
    end

    def alternative_download(dia, mes, ano)
      Capybara.app_host = 'http://pesquisa.in.gov.br/'
      visit "/imprensa/jsp/visualiza/index.jsp?jornal=1&pagina=1&date=#{dia}/#{mes}/#{ano}"

      within_frame 'visualizador' do
        puts find('viewer').text
      end
    end

    def download(day, month, year)

      page.reset_session!

      puts "Step 1: Fetching pdf urls."

      visit "/#leitura_jornais"
      check "chk_avancada_0"
      fill_in "dt_inicio_leitura_jornais", with: "#{day}/#{month}"
      fill_in "dt_fim_leitura_jornais", with: "#{day}/#{month}"

      select year.to_s, :from => "ano_leitura_jornais"

      resultado_busca = window_opened_by do
        click_on "BUSCAR"
      end

      within_window resultado_busca do
        hrefs = find("#ResultadoConsulta").all('a')
        onclick = hrefs[5][:onclick]
        pdf_do1 = hrefs[5][:onclick].to_s[/(http.+')/].to_s.chomp("'")
        pdf_do2 = hrefs[11][:onclick].to_s[/(http.+')/].to_s.chomp("'")
        pdf_do3 = hrefs[17][:onclick].to_s[/(http.+')/].to_s.chomp("'")

        pdf_extra = nil

        if (hrefs.length >= 23)
          pdf_extra = hrefs[23][:onclick].to_s[/(http.+')/].to_s.chomp("'")
        end

        page.execute_script "window.close();"

        urls = [pdf_do1, pdf_do2, pdf_do3]

        Dir.chdir(@tmp_path) do

          puts system('rm *.pdf*')

          File.open("pdfs.links", "w+") do |f|
            urls.each { |element| f.puts(element) }
          end

          puts "Step 2: Downloading pdfs."
          puts system('cat pdfs.links | parallel --no-notice  --gnu "wget -q {}"')
          if (!pdf_extra.nil?)
            puts system("wget -q \"#{pdf_extra}\"")
          end
        end

      end

    rescue Capybara::Poltergeist::TimeoutError
      sleep LONG_SLEEP
      retry
    end

    def parse_file(file)
      attrs = Hash.new

      slices = file.split('_')

      attrs[:year] = slices[1]
      attrs[:month] = slices[2]
      attrs[:day] = slices[3].split('.')[0]

      case slices[4]
        when 'Extra.pdf'
          attrs[:page] = slices[5]
          attrs[:session] = 8
        when 'SuplementoAnvisa.pdf'
          attrs[:page] = slices[5]
          attrs[:session] = 9
        when 'EDJF1'
          attrs[:page] = slices[4]
          attrs[:session] = 14
        else
          attrs[:page] = slices[4]
          attrs[:session] = get_session_id(slices[0])
      end

      attrs
    end

    def insert_db(date)
      puts "Step 5: INSERT into db"

      pgsql = PgSQL.new

      Dir.chdir(@tmp_path) do

        arquivos = Dir.entries('.')
        arquivos.each { |f|

          attr = nil

          attr = parse_file(f) if (f.include? '.pdf')

          if (!attr.nil?)
            content = File.read(f)
            pgsql.insert_document(date, attr[:page], attr[:session], content)
            print "."
          end

        }

      end

      pgsql.destroy();

    end

    def valid_date(data)
      return (!data.sunday? && !data.saturday?)
    end

    def fetch_date(day, month, ano, data)

      prepare
      puts "fetch_date DOU"
      download(day, month, ano)
      clean_file_names();
      split_pages();
      insert_db(data);

    rescue Capybara::ElementNotFound => e
      puts "Skipping date #{data} due to unrecovery error #{e}."
    end

  end
end
