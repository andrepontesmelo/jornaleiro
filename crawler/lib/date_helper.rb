module Jornaleiro
  # Helper methods for date operations
  class DateHelper
    def initialize
      @months = %w(Janeiro Fevereiro Março Abril Maio Junho Julho
                   Agosto Setembro Outubro Novembro Dezembro)
    end

    def month_name(month_number)
      month_number = month_number.to_i if month_number.class == ''.class
      @months[month_number - 1]
    end
  end
end
