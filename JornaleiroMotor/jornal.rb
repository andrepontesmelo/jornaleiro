
module Jornaleiro

  class Jornal

     def obtem_proxima_data(data)
      data -= 1

      while (data.sunday? || data.monday?)
        data -= 1
      end

      data
    end

    def corta_data(data)

      ano = data.year.to_s;
      dia = sprintf('%02.f', data.mday)
      mes = sprintf('%02.f', data.month)

      return dia, mes, ano
    end

    def inicia
      conexao_incial = MySQL.new
      data = conexao_incial.obtem_ultima_data(self.obtem_codigo_jornal)
      conexao_incial.destroy

      while (true)
        data = obtem_proxima_data(data)
        dia, mes, ano = corta_data(data)

        puts " * #{ano}-#{mes}-#{dia}"
        inicia_data(dia, mes, ano, "#{ano}-#{mes}-#{dia}")
      end

    end

  end
end