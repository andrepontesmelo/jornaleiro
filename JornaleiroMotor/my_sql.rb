require 'mysql2'

module Jornaleiro
  class Arquivo
    def le(sessao, data, pagina)
      conteudo = File.read("/tmp/#{sessao}_#{data} #{pagina}.pdf?sequence=1.transcrito")

      conteudo
    end
  end

  class MySQL
    def initialize
      @cliente = Mysql2::Client.new(:host => "localhost", :username => "root",
                                    :password => "", :database => "jornaleiro")
    end

    def limpa_string(conteudo)
      conteudo = conteudo.gsub("'","\\\\'")
      conteudo = conteudo.gsub("\"","\\\\\"")

      conteudo
    end


    def destroy
      #@con.close if @con
    end

    def insere_documento(data, pagina, sessaoId, conteudo, titulo, url)

      conteudo = limpa_string(conteudo)

      if (!titulo.nil?)
        titulo = limpa_string(titulo)
      end

      sql = "INSERT INTO documento(sessao, pagina, data, texto, titulo, url) " +
          " VALUES ( #{sessaoId}, #{pagina}, '#{data}', \"" + conteudo + "\", " +
        (titulo.nil? ? "null" : ("'" + titulo + "'")) + ", " +
      (url.nil? ? "null" : ("'" + url + "'")) + " ) ";

      @cliente.query(sql)

    rescue Mysql2::Error => e
      puts e.errno
      puts e.error
    end

    def obtem_ultima_data(idJornal)

     sql = "select min(data) as data from documento d join sessao s on d.sessao=s.id and s.jornal=#{idJornal} " +
         (idJornal == 1 ? " and data > '2012-06-01'" : " and data > '2012-06-14' ")

     resultado = @cliente.query(sql).entries[0]["data"]

     resultado

    rescue Mysql2::Error => e
      puts e.errno
      puts e.error
    end


    def insere(jornal, data)
      jornal.each { |x|
        sessaoId = x[0]
        sessao = x[1]
        paginas_total = x[2]

        puts "Adicionando ao MySQL #{sessao} (cód. #{sessaoId}) com total de #{paginas_total} páginas"

        arq = Arquivo.new

        for p in 1..paginas_total
          print "#{p}; "
          insere_documento(data, p, sessaoId, arq.le(sessao, data, p), nil, nil)
        end
      }
    end
  end

end
