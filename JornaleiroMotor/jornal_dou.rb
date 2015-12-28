require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative 'my_sql'
require_relative 'jornal'
require_relative 'capybara_util'

#CapybaraUtil.new.SelecionaMotor :selenium
CapybaraUtil.new.SelecionaMotor :poltergeist

Capybara.app_host = 'http://in.gov.br/'

module Jornaleiro

  class JornalDOU < Jornal
    include Capybara::DSL

    def initialize
    end

    def download()
      visit "http://portal.in.gov.br/#leitura_jornais"
      check "chk_avancada_0"
      fill_in "dt_inicio_leitura_jornais", with: "28/12"
      fill_in "dt_fim_leitura_jornais", with: "28/12"

      resultado_busca = window_opened_by do
        click_on "BUSCAR"
      end

      within_window resultado_busca do
        janela_pdf = window_opened_by do

          find("#ResultadoConsulta").all('a')[0].click

          #Get the popup window handle
         popup = page.driver.browser.window_handles.last

          #Then switch control between the windows
          page.driver.browser.switch_to.window(popup)

          within_frame('visualizador'){
            #find("#download").click
            #puts page.response_headers
            #puts page.response_headers['Content-Disposition']

            puts page.text.length
            while (page.text.length < 200)
              sleep 0.5
            end

            puts page.text
          }

        end
      end

#      within_window janela_pdf  do
#        puts "Dentro da janela pdf"
#      end

#puts system("cd /tmp")
#puts system('wget http://download.in.gov.br/do/secao1/2015/2015_12_24/DO1_2015_12_24.pdf')
#puts system('pdftotext DO1_2015_12_23.pdf DO1_2015_12_23.txt')
    end

    def soma(a, b)

      a+b
    end

    JornalDOU.new.download()
  end
end
