require 'fileutils'
require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative '../capybara_util'
require_relative '../jornal'

module Jornaleiro

  class JornalMG < Jornal

    include Capybara::DSL

    def initialize
      super()

      @lista_pdfs = Array.new
      @diretorio_temporario = "/tmp/mg"

      @meses = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"]

      #CapybaraUtil.new.SelecionaMotor(:selenium)
      CapybaraUtil.new.SelecionaMotor(:poltergeist)


      Capybara.app_host = Capybara.default_host = 'http://jornal.iof.mg.gov.br/jspui/UltimoJornal'
    end

    def obtem_codigo_jornal
      1
    end

    def data_possivel(data)
      if (data.sunday? || data.monday?)
        return false
      else
        return true
      end
    end

    def arrumar_encoding(string)
      URI.unescape(string).force_encoding("UTF-8")
    end

    def obtem_sessao(titulo_sessao, sessao_id)
      retorno = {}

      noticiario_window = window_opened_by do
        click_on(titulo_sessao)
      end

      within_window noticiario_window do

        total_paginas = find('#id-div-numero-pagina-corrente').text.split(" ").last.to_i
        if (total_paginas == 0)
          total_paginas = 1
        end

        puts titulo_sessao + " possui #{total_paginas} páginas:"

        for p in 1..total_paginas
          print (p % 10 == 0 ? (p/10) : ".")

          fill_in 'input-pagina-destino', :with => p.to_s
          click_on('ok')

          arquivo_pdf = find('#framePdf')[:src]
          arquivo_pdf = arrumar_encoding(arquivo_pdf)

          arquivo_pdf = arquivo_pdf.split('?')[0]

          retorno[:sessao] = arquivo_pdf.split('/').last.split('_').first
          @lista_pdfs.push(arquivo_pdf)

        end

        page.execute_script "window.close();"

        retorno[:paginas] = total_paginas
      end

      retorno[:sessao_id] = sessao_id
      retorno

    rescue Capybara::ElementNotFound
      puts "Pulando " + titulo_sessao

      if page.windows.count > 1
        within_window noticiario_window do
          page.execute_script "window.close();"
        end
      end

      return nil;
    end

    def jornal_ja_obtido(jornal, sessao_id)
      jornal.each { |j|
        if (j[:sessao_id] == sessao_id)
          return true
        end
      }

      false
    end

    def obter_links(dia, mes, ano, arquivo_links)

      page.reset_session!

      @lista_pdfs.clear()

      visit('http://jornal.iof.mg.gov.br/jspui/UltimoJornal')
      puts ''

      within("#links-constantes-direita") do
        click_on(ano)
      end

      click_on(@meses[mes.to_i - 1])

      within("#id-lista-subcomunidades") do
        link_dia = page.find_link(dia.to_s)

        link_dia.click
      end

      jornalMg = []

      jornalMg.push(obtem_sessao("Noticiário", 1))
      jornalMg.push(obtem_sessao("Publicações de Terceiros", 3))
      jornalMg.push(obtem_sessao("Edição Extra", 13))
      jornalMg.push(obtem_sessao("Diário do Leg", 10))
      jornalMg.push(obtem_sessao("Diário da Jus", 11))
      jornalMg.push(obtem_sessao("Diário do Exec", 2))

      jornalMg.push(obtem_sessao("Anexo", 12))

      Dir.chdir(@diretorio_temporario) do
        File.open(arquivo_links, "w+") do |f|
          @lista_pdfs.each { |element| f.puts(element) }
        end
      end

      jornalMg

    rescue Capybara::Poltergeist::TimeoutError
      puts "TimeoutError."
      sleep 10
      retry
    end

    def baixar(arquivo_links)
      puts ""
      puts("Transcrevendo links => txt do conteudo do pdf..");

      puts "Em " + @diretorio_temporario.to_s

      Dir.chdir(@diretorio_temporario) do

        puts "Obtendo pdfs .."
        linha_comando = "cat #{arquivo_links} | parallel --gnu --no-notice 'wget -q {}'"
        puts linha_comando
        system(linha_comando)

        puts system('du -hs')

        puts "Transcrevendo pdfs para txts."
        system('for file in *.pdf*; do pdftotext -raw "$file" "$file.transcrito"; done')

        puts "Removendo ponto espaço ponto espaço"
        system('for file in *.transcrito; do sed -i -- "s/. . / /g" "$file"; done')

        puts "Removendo espaços e quebras de linha dos documentos"
        system('for file in *.transcrito; do tr "\n" " " < "$file"  | tr -s " " >> "$file".limpo; done')

        puts "Removendo hifenização"
        system('for file in *.limpo; do sed -i -- "s/- //g" "$file"; done')
      end
    end

    def inicia_data(dia, mes, ano, data)

      puts "inicia_data MG"
      prepara_diretorio_temporario

      arquivo_links = "pdfs.txt"

      jornalMg = obter_links(dia, mes, ano, arquivo_links).compact

      if (jornalMg.length > 0)
        baixar(arquivo_links)

        insere("mg", jornalMg, data)

      end

    rescue Capybara::ElementNotFound => e
      puts e
      puts "Pulando dia " + data
    end


    def le(apelido_jornal, sessao, sessao_id, data, pagina)
      if (sessao_id == 8)
        sessao = 'Edição-Extra'
      end

      arquivos = Dir["/tmp/mg/#{sessao}*.limpo"]

      if arquivos.size == 1
        nome_arquivo = arquivos[0]
      else

        arquivos = Dir["/tmp/mg/#{sessao}* #{pagina}.pdf.transcrito.limpo"]

        if arquivos.size == 1
          nome_arquivo = arquivos[0]
        else
          puts "Implementar: Dir " + "/tmp/mg/#{sessao}* #{pagina}.pdf.transcrito.limpo"
          puts "Retornou " + arquivos.size.to_s + " registros. "
          exit 1
        end
      end

      conteudo = File.read(nome_arquivo)

      conteudo

    end

    def insere(apelido_jornal, jornal, data)
      pgsql = PgSQL.new

      jornal.each { |x|

        sessao_id = x[:sessao_id]
        sessao = x[:sessao]
        paginas_total = x[:paginas]

        for p in 1..paginas_total
          print "."
          pgsql.insere_documento(data, p, sessao_id, le(apelido_jornal, sessao, sessao_id, data, p), nil, nil)
        end
      }

      pgsql.destroy()
    end

  end
end
