require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../my_sql'
require_relative '../jornal'
require_relative '../capybara_util'

#CapybaraUtil.new.SelecionaMotor :selenium
CapybaraUtil.new.SelecionaMotor :poltergeist

Capybara.app_host = 'http://portal.in.gov.br'

module Jornaleiro

  class JornalDOU < Jornal
    include Capybara::DSL

    def initialize
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


    def limpar_arquivos()
      puts "Passo 3: Limpando nomes dos arquivos pdf."
      arquivos = Dir.entries(".")

      arquivos.each { |arquivo|
        pdf_limpo = arquivo[/\w+.pdf/]

        if (!pdf_limpo.nil?)
          puts pdf_limpo

          File.rename(arquivo, pdf_limpo)
        end
      }
    end

    def divide_paginas
      puts "Passo 4: Dividindo pdfs por página"
      arquivos = Dir.entries(".")

      arquivos.each { |arquivo|
        pdf = arquivo[/\w+.pdf/]

        if (!pdf.nil?)
          saida = `pdfinfo #{pdf} | grep Pages:`
          total_paginas = saida.split(" ").last.to_i;

          for p in 1..total_paginas
            resultado = system("pdftotext -f #{p} -l #{p} #{pdf} #{pdf}_#{p}")
          end
        end
      }
    end


    def download(dia, mes)
      page.reset_session!

      puts "Passo 1: Fazendo o download das urls dos pdf."

      visit "/#leitura_jornais"
      check "chk_avancada_0"
      fill_in "dt_inicio_leitura_jornais", with: "#{dia}/#{mes}"
      fill_in "dt_fim_leitura_jornais", with: "#{dia}/#{mes}"

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
        begin
          puts system('rm *.pdf*')

          File.open("pdfs.links", "w+") do |f|
            urls.each { |element| f.puts(element) }
          end

          puts "Passo 2: Baixando pdfs."
          puts system('cat pdfs.links | parallel --gnu "wget {}"')
          if (!pdf_do_extra.nil?)
            puts system("wget \"#{pdf_do_extra}\"")
          end
        end
      end

#      page.switch_to_window(page.windows[0])
#      page.execute_script "window.close();"
    end

    def interpreta_arquivo(arquivo)
      atributos = Hash.new

      partes = arquivo.split('_')
      atributos[:ano] = partes[1]
      atributos[:mes] = partes[2]
      atributos[:dia] = partes[3].chomp(".pdf")

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

=begin
        puts arquivo
        puts "Ano = #{atributos[:ano]}"
        puts "Mes = #{atributos[:mes]}"
        puts "Dia = #{atributos[:dia]}"
        puts "Página = #{atributos[:pagina]}"
        puts "Sessão " + atributos[:sessao].to_s
        puts "====================================="
=end

      atributos
    end

    def insere_mysql(data)
      puts "Passo 5: Inserindo no MySQL"

      mysql = MySQL.new

      arquivos = Dir.entries(".")
      arquivos.each { |arquivo|

        atributos = nil

        dou = arquivo[/\DO\d_(\d)(\d)(\d)(\d)_(\d)(\d)_(\d)(\d).pdf_(\d)*/]
        if (!dou.nil?)
          atributos = interpreta_arquivo(dou)
        end

        dou_extra = arquivo[/\DO\d_(\d)(\d)(\d)(\d)_(\d)(\d)_(\d)(\d)_Extra.pdf_(\d)*/]
        if (!dou_extra.nil?)
          atributos = interpreta_arquivo(dou_extra)
        end

        dou_suplemento_anvisa = arquivo[/\DO\d_(\d)(\d)(\d)(\d)_(\d)(\d)_(\d)(\d)_SuplementoAnvisa.pdf_(\d)*/]
        if (!dou_suplemento_anvisa.nil?)
          atributos = interpreta_arquivo(dou_suplemento_anvisa)
        end

        if (!atributos.nil?)
          conteudo = File.read(arquivo)
          mysql.insere_documento(data, atributos[:pagina], atributos[:sessao], conteudo, nil, nil)
          print "."
        end
      }

      mysql.destroy();

    end

    def data_possivel(data)
      if (data.sunday? || data.saturday?)
        return false
      else
        return true
      end
    end


    def inicia_data(dia, mes, ano, data)

      download(dia, mes)
      limpar_arquivos();
      divide_paginas();
      insere_mysql(data);

    rescue Capybara::ElementNotFound => e
      puts e
      puts "Pulando dia " + data
    end

  end

  JornalDOU.new.inicia();

end
