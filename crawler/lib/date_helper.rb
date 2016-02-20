module Jornaleiro
  # Helper methods for date operations
  class DateHelper
    def initialize
      @months = %w(Janeiro Fevereiro Março Abril Maio Junho Julho
                   Agosto Setembro Outubro Novembro Dezembro)
    end

    def month_name(month_number)
      @months[month_number - 1]
    end
  end
end
