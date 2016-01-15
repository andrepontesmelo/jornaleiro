require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require 'json'
require_relative '../pg_sql'
require_relative '../capybara_util'
require_relative '../jornal'


module Jornaleiro

  class JornalBH < Jornal

    include Capybara::DSL

    def initialize
      super()

      CapybaraUtil.new.SelecionaMotor(:poltergeist)
      Capybara.app_host = Capybara.default_host = 'http://portal6.pbh.gov.br'
    end


    def data_possivel(data)
      if (data.sunday? || data.monday?)
        return false
      else
        return true
      end
    end

    def obtem_codigo_jornal
      2
    end

    def inicia_data(dia, mes, ano, data)

      puts "inicia_data BH"
      lista_resultado = obter_conteudo(dia, mes, ano)

      if (!lista_resultado.nil?)
        pgsql = PgSQL.new

        lista_resultado.each_with_index { |artigo, indice|

          pgsql.insere_documento(data, indice, 4, artigo[1], artigo[0], artigo[2])

        }

        pgsql.destroy()
      end
    end


    def obter_conteudo(dia, mes, ano)

      lista_resultado = Array.new();

      page.reset_session!

      visit("/dom/iniciaEdicao.do?method=DomDia&dia=" + dia + "/" + mes + "/" + ano + "&comboAno=" + ano)

      find('#imgExtRecTodos').click

      artigos = all(:css, ".ChamadaArtigo")

      puts " " * artigos.length + "|"

      artigos.each { |artigo|

        artigo_resultado = Array.new();

        artigo_resultado.push(artigo.text)

        print '.'

        url = artigo.first("a")["href"]

        within_window open_new_window do
          visit(url)

          artigo_resultado.push(all("#esquerda")[1].text)
          artigo_resultado.push(url)
          page.execute_script "window.close();"
        end


        lista_resultado.push(artigo_resultado);

      }

      puts ""

      lista_resultado

    rescue Capybara::ElementNotFound, Capybara::Poltergeist::TimeoutError, Capybara::Poltergeist::DeadClient => e
      puts e.message
    end

  end

end




