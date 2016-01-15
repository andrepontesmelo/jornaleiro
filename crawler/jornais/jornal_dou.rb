require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative '../jornal'
require_relative '../capybara_util'

module Jornaleiro

  class JornalDOU < Jornal
    include Capybara::DSL

    def initialize
      super()

      @diretorio_temporario = "/tmp/dou"

      CapybaraUtil.new.SelecionaMotor :poltergeist
      Capybara.app_host = 'http://portal.in.gov.br'
    end

    def obtem_codigo_jornal
      3
    end

    def obtem_sessao(dom)
      case dom
        when "DO1"
          5
        when "DO2"
          6
        when "DO3"
          7
        else
          puts dom
          raise StandardError
      end
    end

    def limpar_arquivo(nome_arquivo)
      nome_arquivo = nome_arquivo[/\w+.pdf/]

      nome_arquivo
    end

    def limpar_arquivos()
      puts "Passo 3: Limpando nomes dos arquivos pdf."
      Dir.chdir(@diretorio_temporario) do

        arquivos = Dir.entries(".")

        arquivos.each { |arquivo|

          pdf_limpo = limpar_arquivo(arquivo)

          if (!pdf_limpo.nil?)
            puts "Renomeando " + arquivo + " para " + pdf_limpo
            File.rename(arquivo, pdf_limpo)
          end
        }

      end
    end


    def divide_paginas
      puts "Passo 4: Dividindo pdfs por página"
      Dir.chdir(@diretorio_temporario) do
        arquivos = Dir.entries(".")

        arquivos.each { |arquivo|
          pdf = arquivo[/\w+.pdf/]

          if (!pdf.nil?)
            saida = `pdfinfo #{pdf} | grep Pages:`
            total_paginas = saida.split(" ").last.to_i;

            puts "Transcrevendo pdfs para txts, limpando espaços e quebras de linha e hifenização:"

            for p in 1..total_paginas
              print '.'
              pdf_com_pagina = "#{pdf}_#{p}"
              pdf_limpo = "#{pdf_com_pagina}_limpo"

              system("pdftotext -raw -f #{p} -l #{p} #{pdf} #{pdf_com_pagina} ")
              system("tr '\n' ' ' < #{pdf_com_pagina} | tr -s ' ' >> #{pdf_limpo}")
              system("sed -i -- 's/- //g' '#{pdf_limpo}'")
              system("mv #{pdf_limpo} #{pdf_com_pagina}")
            end

            puts "mv #{@diretorio_temporario}/#{arquivo} /home/dou/#{arquivo} "
            system("mv #{@diretorio_temporario}/#{arquivo} /home/dou/#{arquivo} ")
          end
        }
      end
    end


    def download(dia, mes, ano)

      page.reset_session!

      puts "Passo 1: Fazendo o download das urls dos pdf."

      visit "/#leitura_jornais"
      check "chk_avancada_0"
      fill_in "dt_inicio_leitura_jornais", with: "#{dia}/#{mes}"
      fill_in "dt_fim_leitura_jornais", with: "#{dia}/#{mes}"

      select ano.to_s, :from => "ano_leitura_jornais"

      resultado_busca = window_opened_by do
        click_on "BUSCAR"
      end

      within_window resultado_busca do
        todos = find("#ResultadoConsulta").all('a')
        onclick = todos[5][:onclick]
        pdf_do1 = todos[5][:onclick].to_s[/(http.+')/].to_s.chomp("'")
        pdf_do2 = todos[11][:onclick].to_s[/(http.+')/].to_s.chomp("'")
        pdf_do3 = todos[17][:onclick].to_s[/(http.+')/].to_s.chomp("'")

        pdf_do_extra = nil

        if (todos.length >= 23)
          pdf_do_extra = todos[23][:onclick].to_s[/(http.+')/].to_s.chomp("'")
        end

        page.execute_script "window.close();"

        urls = [pdf_do1, pdf_do2, pdf_do3]

        prepara_diretorio_temporario()

        Dir.chdir(@diretorio_temporario) do

          puts system('rm *.pdf*')

          File.open("pdfs.links", "w+") do |f|
            urls.each { |element| f.puts(element) }
          end

          puts "Passo 2: Baixando pdfs."
          puts system('cat pdfs.links | parallel --no-notice  --gnu "wget -q {}"')
          if (!pdf_do_extra.nil?)
            puts system("wget -q \"#{pdf_do_extra}\"")
          end
        end

      end

    rescue Capybara::Poltergeist::TimeoutError
      puts "TimeoutError."
      sleep 10
      retry
    end

    def interpreta_arquivo(arquivo)
#      puts "Interpretando : '#{arquivo}'"
      atributos = Hash.new

      partes = arquivo.split('_')

      atributos[:ano] = partes[1]
      atributos[:mes] = partes[2]
      atributos[:dia] = partes[3].split('.')[0]

      if (partes[4].eql? "Extra.pdf")
        atributos[:pagina] = partes[5]
        atributos[:sessao] = 8
      else
        if (partes[4].eql? "SuplementoAnvisa.pdf")
          atributos[:pagina] = partes[5]
          atributos[:sessao] = 9
        else
          atributos[:pagina] = partes[4]
          atributos[:sessao] = obtem_sessao(partes[0])
        end
      end

#      puts puts atributos

      atributos
    end

    def insere_bd(data)
      puts "Passo 5: Inserindo no Banco de Dados"

      pgsql = PgSQL.new

      Dir.chdir(@diretorio_temporario) do

        arquivos = Dir.entries('.')
        arquivos.each { |arquivo|

          atributos = nil

          if (arquivo.include? '.pdf')
            atributos = interpreta_arquivo(arquivo)
          end

          if (!atributos.nil?)
            conteudo = File.read(arquivo)
            pgsql.insere_documento(data, atributos[:pagina], atributos[:sessao], conteudo, nil, nil)
            print "."
          else
            puts "Ignorando arquivo: " + arquivo
          end
        }

      end

      pgsql.destroy();

    end

    def data_possivel(data)
      if (data.sunday? || data.saturday?)
        return false
      else
        return true
      end
    end


    def inicia_data(dia, mes, ano, data)

      puts "inicia_data DOU"
      download(dia, mes, ano)
      limpar_arquivos();
      divide_paginas();
      insere_bd(data);

    rescue Capybara::ElementNotFound => e
      puts e
      puts "Pulando dia " + data
    end

  end
end
