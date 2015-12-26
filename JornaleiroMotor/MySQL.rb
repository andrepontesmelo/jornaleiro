#!/usr/bin/ruby
require 'mysql2'

module Jornaleiro
  class Arquivo
    def le(sessao, data, pagina)
      contents = File.read("#{sessao}_#{data} #{pagina}.pdf?sequence=1.transcrito")
      contents = contents.gsub("'","\\\\'")
      contents = contents.gsub("\"","\\\\\"")
      contents
    end
  end

  class MySQL
    def initialize
      @cliente = Mysql2::Client.new(:host => "localhost", :username => "root",
                                    :password => "", :database => "jornaleiro")
    end

    def destroy
      #@con.close if @con
    end

    def insere_documento(data, pagina, sessaoId, conteudo)
      sql = "INSERT INTO documento(sessao, pagina, data, texto) " +
          " VALUES ( #{sessaoId}, #{pagina}, '#{data}', \"" + conteudo + "\")";

      @cliente.query(sql)

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
          insere_documento(data, p, sessaoId, arq.le(sessao, data, p))
        end
      }
    end
  end

end