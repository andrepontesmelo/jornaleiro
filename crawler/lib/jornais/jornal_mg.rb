require 'fileutils'
require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative '../capybara_util'
require_relative '../jornal'
require_relative '../meta_documentos'

module Jornaleiro

  class JornalMG < Jornal

    include Capybara::DSL

    ARQUIVO_LINKS = "pdfs.txt"
    APELIDO_JORNAL = 'mg'

    # String de localização de links para as sessões
    STR_NOTICIARIO = "Noticiário"
    STR_TERCEIROS = "Publicações de Terceiros"
    STR_EXTRA = "Edição Extra"
    STR_LEG = "Diário do Leg"
    STR_JUS = "Diário da Jus"
    STR_EXEC = "Diário do Exec"
    STR_ANEXO = "Anexo"

    # Código das sessões no banco de dados
    SESSAO_NOTICIARIO = 1
    SESSAO_TERCEIROS = 3
    SESSAO_EXTRA = 13
    SESSAO_LEG = 10
    SESSAO_JUS = 11
    SESSAO_EXEC = 2
    SESSAO_ANEXO = 12

    def initialize
      super()

      @recuperacao = {}
      @documentos = MetaDocumentos.new
      @diretorio_temporario = "/tmp/#{APELIDO_JORNAL}"

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

    def gera_chave(sessao_id, pagina)
      return "#{sessao_id}_#{pagina}"
    end

    def obtem_sessao(titulo_sessao, sessao_id)
      meta = MetaDocumento.new(sessao_id)

      noticiario_window = window_opened_by do
        click_on(titulo_sessao)
      end

      within_window noticiario_window do
        sleep 5
        total_paginas = find('#id-div-numero-pagina-corrente').text.split(" ").last.to_i
        if (total_paginas == 0 || total_paginas == 200 || sessao_id == SESSAO_ANEXO)
          total_paginas = 1
        end

        puts "Temos #{@recuperacao.size} páginas recuperáveis. "
        puts titulo_sessao + " possui #{total_paginas} páginas:"

        for p in 1..total_paginas
          sleep 2
          recuperado = @recuperacao[gera_chave(p, sessao_id)]
          if (recuperado.nil?)


            print (p % 10 == 0 ? (p/10) : ".")

            fill_in 'input-pagina-destino', :with => p.to_s
            sleep 1
            click_on('ok')
            sleep 1

            arquivo_pdf = find('#framePdf')[:src]
            arquivo_pdf = arrumar_encoding(arquivo_pdf)

            arquivo_pdf = arquivo_pdf.split('?')[0]

            recuperado = [arquivo_pdf, arquivo_pdf.split('/').last.split('_').first]
            @recuperacao[gera_chave(p, sessao_id)] = recuperado;
          end

          meta.sessao = recuperado[1]
          meta.lista_pdfs.push(recuperado[0])
        end

        page.execute_script "window.close();"

        meta.paginas = total_paginas
      end

      meta

    rescue Capybara::ElementNotFound => e
      puts "Pulando: #{titulo_sessao} - #{e.message}\n"

      if page.windows.count > 1
        within_window noticiario_window do
          page.execute_script "window.close();"
        end
      end

      if (e.message.include? 'framePdf')
        puts "Vou tentar novamente."
        raise StandardError
      else
        puts "Nao vou tentar novamente."
        return meta;
      end

    end

    def obter_meta_documentos(dia, mes, ano)

      puts "Obtendo #{dia}/#{mes}/#{ano}"
      page.reset_session!

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

      @documentos.adiciona(obtem_sessao(STR_TERCEIROS, SESSAO_TERCEIROS)) unless @documentos.contem_sessao(SESSAO_TERCEIROS)

      @documentos.adiciona(obtem_sessao(STR_NOTICIARIO, SESSAO_NOTICIARIO)) unless @documentos.contem_sessao(SESSAO_NOTICIARIO)

      @documentos.adiciona(obtem_sessao(STR_EXTRA, SESSAO_EXTRA)) unless @documentos.contem_sessao(SESSAO_EXTRA)

      @documentos.adiciona(obtem_sessao(STR_LEG, SESSAO_LEG)) unless @documentos.contem_sessao(SESSAO_LEG)

      @documentos.adiciona(obtem_sessao(STR_JUS, SESSAO_JUS)) unless @documentos.contem_sessao(SESSAO_JUS)

      @documentos.adiciona(obtem_sessao(STR_EXEC, SESSAO_EXEC)) unless @documentos.contem_sessao(SESSAO_EXEC)

      @documentos.adiciona(obtem_sessao(STR_ANEXO, SESSAO_ANEXO)) unless @documentos.contem_sessao(SESSAO_ANEXO)

      @documentos

    rescue Capybara::Poltergeist::TimeoutError, StandardError => e
      if (!e.message.eql?("Unable to find link \"#{dia.to_s}\""))

        puts e.message
        sleep 10
        retry
      else
        puts "Dia #{dia.to_s} não possui jornal oficial."
        return nil;
      end
    end

    def baixar_links()
      puts("\nTranscrevendo links => txt do conteudo do pdf..");
      puts "Em " + @diretorio_temporario.to_s

      Dir.chdir(@diretorio_temporario) do

        puts "Obtendo pdfs .."
        linha_comando = "cat #{ARQUIVO_LINKS} | parallel --gnu --no-notice 'wget -q {}'"
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

    def salva_arquivo_links
      Dir.chdir(@diretorio_temporario) do
        File.open(ARQUIVO_LINKS, "w+") do |f|
          @documentos.obtem_lista_pdfs.each { |element| f.puts(element) }
        end
      end
    end

    def insere(documentos, data)
      pgsql = PgSQL.new

      documentos.obtem_lista.each { |documento|

        if (!documento.paginas.nil?)

          sessao_id = documento.sessao_id
          sessao = documento.sessao
          paginas_total = documento.paginas

          for p in 1..paginas_total
            print "."
            pgsql.insere_documento(data, p, sessao_id, le(APELIDO_JORNAL, sessao, sessao_id, data, p), nil, nil)
          end
        end
      }

      pgsql.destroy()
    end

    def inicia_data(dia, mes, ano, data)

      puts "inicia_data MG"
      prepara_diretorio_download
      @documentos = MetaDocumentos.new
      @recuperacao = {}
      documentos = obter_meta_documentos(dia, mes, ano)
      if (!documentos.nil?)
        salva_arquivo_links

        baixar_links() if (documentos.obtem_lista_pdfs.length > 0)

        insere(documentos, data)
      end

    end

  rescue Capybara::ElementNotFound => e
    puts e
    puts "Pulando dia " + data
  end

end
