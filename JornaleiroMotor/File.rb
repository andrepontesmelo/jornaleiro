require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative 'MySQL'

begin
  require 'capybara/poltergeist'
  Capybara.javascript_driver = :poltergeist
  Capybara.current_driver = :poltergeist
  Capybara.run_server = false|

      options = {js_errors: false}
  Capybara.register_driver :poltergeist do |app|
    Capybara::Poltergeist::Driver.new(app, options)
  end
end

=begin
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
=end

Capybara.app_host = 'http://jornal.iof.mg.gov.br/'
Capybara.default_host = 'http://jornal.iof.mg.gov.br/'

module Jornaleiro

  class JornalMg
    include Capybara::DSL

    def initialize
      @lista_pdfs = Array.new
    end


    def obtem_sessao(titulo_sessao)
      noticiario_window = window_opened_by do
        click_on(titulo_sessao)
      end

      within_window noticiario_window do
        total_paginas = find('#id-div-numero-pagina-corrente').text.split(" ").last.to_i

        puts ""

        puts titulo_sessao + " possui #{total_paginas} páginas "

        puts total_paginas.to_s + " páginas: "
        for p in 1..total_paginas
          print (p % 10 == 0 ? (p/10) : ".")

          fill_in 'input-pagina-destino', :with => p.to_s
          click_on('ok')

          @lista_pdfs.push(find('#framePdf')[:src])
        end

        page.execute_script "window.close();"

        total_paginas
      end
    end


    def obter_links(dia)

      puts ""
      puts "Obtendo dia " + dia
      visit('/jspui/UltimoJornal')

      within("#id-pilha-navegacao") do
        click_on('2015')
      end

      click_on('Julho')

      within("#id-lista-subcomunidades") do
        link_dia = page.find_link(dia.to_s)

        link_dia.click
      end

      jornalMg = []

      jornalMg.push([3, 'caderno2', obtem_sessao("Publicações de Terceiros e Editais de Comarcas")])
      jornalMg.push([1, 'noticiario', obtem_sessao("Noticiário")])
      jornalMg.push([2, 'caderno1', obtem_sessao("Diário do Executiv")])

      puts @lista_pdfs
      puts jornalMg

      File.open("pdfs.links", "w+") do |f|
        @lista_pdfs.each { |element| f.puts(element) }
      end

      jornalMg
    end

    def transcrever()
      puts system('rm *.transcrito')
      puts system('rm *.pdf*')
      puts system('cat pdfs.links | parallel --gnu "wget {}"')
      puts system('for file in *.pdf*; do pdftotext "$file" "$file.transcrito"; done')
    end
  end



  for dia in 26..31
    begin

      dia = sprintf('%02.f', dia)
      data = '2015-07-' + dia.to_s
      t = JornalMg.new

      jornalMg = t.obter_links(dia) # Dia

      t.transcrever

      mysql = MySQL.new
      mysql.insere(jornalMg, data)
      mysql.destroy()
    rescue Capybara::ElementNotFound
      puts "Pulando dia " + data
      next
    end
  end


end

=begin
require 'capybara/poltergeist'
Capybara.javascript_driver = :poltergeist
Capybara.app_host = 'http://www.google.com'
visit("/")
=end

#select * from documento where match(texto) against('+"Martins Pontes"' in boolean mode);
