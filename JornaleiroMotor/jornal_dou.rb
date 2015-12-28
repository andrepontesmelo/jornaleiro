require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative 'MySQL'

Capybara.current_driver = :selenium
Capybara::Selenium::Driver.class_eval do
  def quit
    puts "Press RETURN to quit the browser"
    $stdin.gets
    @browser.quit
  rescue Errno::ECONNREFUSED
    # Browser must have already gone
  end
end

Capybara.app_host = 'http://in.gov.br/'

class JornalDOU
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


    end

#puts system("cd /tmp")
#puts system('wget http://download.in.gov.br/do/secao1/2015/2015_12_24/DO1_2015_12_24.pdf')
#puts system('pdftotext DO1_2015_12_23.pdf DO1_2015_12_23.txt')
  end

  def soma(a, b)

    a+b
  end

  JornalDOU.new.download()
end