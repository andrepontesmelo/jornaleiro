require 'sequel'

module Jornaleiro
  class PgSQL
    def initialize
      @cliente = Sequel.postgres(:user=>'jornaleiro',:password=>'',:host=>'localhost',:port=>5432)
    end

    def limpa_string(conteudo)
      conteudo = conteudo.gsub("\"","'")
      conteudo = conteudo.gsub("'","\\\\'")

      while (conteudo.include? '--') do
        conteudo = conteudo.gsub('--','-')
      end

      while (conteudo.include? '..') do
        conteudo = conteudo.gsub('..','.')
      end

      # See String#encode
      encoding_options = {
          :invalid           => :replace,  # Replace invalid byte sequences
          :undef             => :replace,  # Replace anything not defined in ASCII
          :replace           => '',        # Use a blank for those replacements
          :universal_newline => true       # Always break lines with \n
      }

      conteudo.encode!(Encoding.find('UTF-8'), encoding_options)

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
                        :textominusculo => conteudo,
                        :titulo => titulo,
                        :url => url)

    rescue Exception => e
        puts "Erro ao inserir no banco de dados !"
        puts "Data: #{data} pagina: #{pagina}, sessaoId: #{sessaoId} titulo #{titulo} url #{url}"

      puts e
      exit 1;
    end

    def obtem_ultima_data(idJornal, ordem)

     minmax = (ordem == :recentes ? "max" : "min")

     sql = "select #{minmax}(data) as data from documento d join sessao s on d.sessao=s.id and s.jornal=#{idJornal} "

     resultado = Date.parse(@cliente.fetch(sql).first[:data].to_s)

     puts sql
     puts resultado

     resultado

    rescue Exception => e
      puts "Não há nenhuma data no banco de dados."
     return nil;
    end

  end

end
