require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative 'my_sql'
require_relative 'capybara_util'
require_relative 'jornal'

#CapybaraUtil.new.SelecionaMotor(:selenium)
CapybaraUtil.new.SelecionaMotor(:poltergeist)


Capybara.app_host = Capybara.default_host = 'http://jornal.iof.mg.gov.br/jspui/UltimoJornal'

module Jornaleiro

  class JornalMg < Jornal

    include Capybara::DSL

    def initialize
      @lista_pdfs = Array.new

      @meses = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"]
    end

    def obtem_codigo_jornal
      1
    end

    def obtem_proxima_data(data)
      data -= 1

      while (data.sunday? || data.monday?)
        data -= 1
      end

      data
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


    def obter_links(dia, mes, ano)

	puts ("Obtendo links para dia #{dia} do mes #{mes}, ano #{ano}")
      
 	page.reset_session!
        @lista_pdfs.clear()


#      //xmlui/handle/123456789/136529
      visit('http://jornal.iof.mg.gov.br/jspui/UltimoJornal')

      within("#links-constantes-direita") do
        click_on(ano)
      end

      click_on(@meses[mes.to_i - 1])

      within("#id-lista-subcomunidades") do
        link_dia = page.find_link(dia.to_s)

        link_dia.click
      end

      jornalMg = []

      jornalMg.push([3, 'caderno3', obtem_sessao("Publicações de Terceiros")])
      jornalMg.push([1, 'noticiario', obtem_sessao("Noticiário")])
      jornalMg.push([2, 'caderno1', obtem_sessao("Diário do Executiv")])

      puts "Lista dos pdfs:"
      puts @lista_pdfs
      puts jornalMg

#      File.delete("/tmp/pdfs.links");
      File.open("/tmp/pdfs.links", "w+") do |f|
        @lista_pdfs.each { |element| f.puts(element) }
      end

      jornalMg

    rescue Capybara::Poltergeist::TimeoutError
      sleep 10
      retry
    end

    def transcrever()
	puts("Transcrevendo links => txt do conteudo do pdf..");

      Dir.chdir("/tmp") do
        puts system(' rm *.transcrito')
        puts system('rm *.pdf*')
        puts system('cat pdfs.links | parallel --gnu "wget {}"')
        puts system('for file in *.pdf*; do pdftotext "$file" "$file.transcrito"; done')
      end
    end

    def inicia_data(dia, mes, ano, data)

      jornalMg = obter_links(dia, mes, ano)
      transcrever

      mysql = MySQL.new
      mysql.insere(jornalMg, data)
      mysql.destroy()

    rescue Capybara::ElementNotFound => e
      puts e
      puts "Pulando dia " + data
    end

  end

  JornalMg.new.inicia
=begin
 ano = 2012

 for mes in 5..5
  for dia in 10..31
    begin

      dia = sprintf('%02.f', dia)
      mes = sprintf('%02.f', mes)
      data = ano.to_s + '-' + mes.to_s + '-' + dia.to_s
      t = JornalMg.new

      jornalMg = t.obter_links(dia, mes, ano) # Dia

      t.transcrever

      mysql = MySQL.new
      mysql.insere(jornalMg, data)
      mysql.destroy()
    rescue Capybara::ElementNotFound => e
      puts e
      puts "Pulando dia " + data
#throw e
      next
    end
  end
 end
=end

end

=begin
require 'capybara/poltergeist'
Capybara.javascript_driver = :poltergeist
Capybara.app_host = 'http://www.google.com'
visit("/")
=end

#select * from documento where match(texto) against('+"Martins Pontes"' in boolean mode);
