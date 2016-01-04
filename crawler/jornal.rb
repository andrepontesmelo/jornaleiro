
module Jornaleiro

  class Jornal

    def obtem_proxima_data(data, ordem)
      puts ordem
      incremento = (ordem == :mais_antigos ? -1 : 1)

      data += incremento

      while (!data_possivel(data))
        data += incremento
      end

      if (data > Date.today)
        data = nil
      end

      data
    end


    def data_possivel(data)
      if (data.sunday? || data.monday?)
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

    def inicia
      conexao_incial = MySQL.new

      ordem = :mais_recentes

      data = conexao_incial.obtem_ultima_data(self.obtem_codigo_jornal, ordem)
      conexao_incial.destroy

      while (true)
        data = obtem_proxima_data(data, ordem)
        dia, mes, ano = corta_data(data)

        puts " * #{ano}-#{mes}-#{dia}"
        inicia_data(dia, mes, ano, "#{ano}-#{mes}-#{dia}")
      end

    end

  end
end
