require 'sequel'
require_relative 'arquivo'

module Jornaleiro
  class PgSQL
    def initialize
      @cliente = Sequel.postgres(:user=>'jornaleiro',:password=>'',:host=>'localhost',:port=>5432)
    end

    def limpa_string(conteudo)
      conteudo = conteudo.gsub("\"","'")
      conteudo = conteudo.gsub("'","\\\\'")
      conteudo
    end

    def destroy
      @cliente.disconnect
    end

    def insere_documento(data, pagina, sessaoId, conteudo, titulo, url)

      conteudo = limpa_string(conteudo)

      if (!titulo.nil?)
        titulo = limpa_string(titulo)
      end

      documentos = @cliente[:documento] # Create a dataset

      documentos.insert(:sessao => sessaoId,
                        :pagina => pagina,
                        :data => data,
                        :texto => conteudo,
                        :textominusculo => conteudo.downcase,
                        :titulo => titulo,
                        :url => url)

    rescue Exception => e
      puts e
    end

    def obtem_ultima_data(idJornal, ordem)

     minmax = (ordem == :mais_recentes ? "max" : "min")

     sql = "select #{minmax}(data) as data from documento d join sessao s on d.sessao=s.id and s.jornal=#{idJornal} "

     puts sql

     resultado = Date.parse(@cliente.fetch(sql).first[:data].to_s)

     puts resultado
     resultado

    rescue Exception => e
     puts e
    end


    def insere(jornal, data)
      jornal.each { |x|
        sessaoId = x[0]
        sessao = x[1]
        paginas_total = x[2]

        puts "Adicionando ao PostgreSQL #{sessao} (cód. #{sessaoId}) com total de #{paginas_total} páginas"

        arq = Arquivo.new

        for p in 1..paginas_total
          print "#{p}; "
          insere_documento(data, p, sessaoId, arq.le(sessao, data, p), nil, nil)
        end
      }
    end
  end

end
