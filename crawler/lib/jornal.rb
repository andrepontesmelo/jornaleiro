require 'date'

module Jornaleiro

  class Jornal
    attr_accessor :ordem

    def initialize
      @ordem = :recentes
    end

    def le(apelido_jornal, sessao, sessao_id, data, pagina)
      if (sessao_id == 8)
        sessao = 'Edição-Extra'
      end

      arquivos = Dir["/tmp/#{apelido_jornal}/#{sessao}*.limpo"]

      if arquivos.size == 1
        nome_arquivo = arquivos[0]
      else

        arquivos = Dir["/tmp/#{apelido_jornal}/#{sessao}* #{pagina}.pdf.transcrito.limpo"]

        if arquivos.size == 1
          nome_arquivo = arquivos[0]
        else
          puts "Implementar: Dir " + "/tmp/#{apelido_jornal}/#{sessao}* #{pagina}.pdf.transcrito.limpo"
          puts "Retornou " + arquivos.size.to_s + " registros. "
          exit 1
        end
      end

      conteudo = File.read(nome_arquivo)
      conteudo

    end

    def valida_ordem(ordem)
      if (ordem != :antigos && ordem != :recentes)
        raise StandardError
      end
    end

    def prepara_diretorio_download
      system(" rm -rf #{@diretorio_temporario} ")
      FileUtils::mkdir_p "#{@diretorio_temporario}"
    end

    def data_possivel(data)
      if (data.sunday? || data.saturday?)
        return false
      else
        return true
      end
    end

    def corta_data(data)

      ano = data.year.to_s;
      dia = sprintf('%02.f', data.mday)
      mes = sprintf('%02.f', data.month)

      return dia, mes, ano
    end


    def obtem_proxima_data_na_ordem(data)
      incremento = (@ordem == :antigos ? -1 : 1)

      data += incremento

      while (!data_possivel(data))
        data += incremento
      end

      data = nil if (data > Date.today)

      data
    end

    def obtem_proxima_data(data)

      #return Date.parse('1997-01-16')

      if (data.nil?)
        return Date.today
      end

      proxima = obtem_proxima_data_na_ordem(data)

      if (proxima.nil?)

        @ordem = :antigos
        puts "Ordem alterada: Pegando antigos!"

        data = obtem_ultima_data

        return (data.nil? ? Date.today : obtem_proxima_data_na_ordem(data))
      end

      proxima
    end

    def obtem_ultima_data
      conexao = PgSQL.new
      data = conexao.obtem_ultima_data(self.obtem_codigo_jornal, @ordem)
      conexao.destroy

      data
    end

    def inicia()

      conexao_incial = PgSQL.new

      valida_ordem(@ordem)

      data = obtem_ultima_data
      conexao_incial.destroy

      while (true)

        puts "Data = #{data}"
        data = obtem_proxima_data(data)

        puts "Proxima data: #{data}"
        dia, mes, ano = corta_data(data)

        puts " * #{ano}-#{mes}-#{dia}"
        inicia_data(dia, mes, ano, "#{ano}-#{mes}-#{dia}")
      end
    end

  end
end
