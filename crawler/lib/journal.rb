require 'date'

module Jornaleiro

  class Journal
    attr_accessor :order

    def initialize
      @order = :new
    end

    def read_file(journal_name, session, session_id, page)
      if (session_id == 8)
        session = 'Edição-Extra'
      end

      files = Dir["#{@tmp_path}/#{session}*.limpo"]

      if files.size == 1
        filename = files[0]
      else

        files = Dir["#{@tmp_path}/#{session}* #{page}.pdf.transcrito.limpo"]

        if files.size == 1
          filename = files[0]
        else
          puts "Not implemented: Dir #{@tmp_path}/#{session}* #{page}.pdf.transcrito.limpo"
          puts "Returned #{files.size.to_s} files. "
          exit 1
        end
      end

      content = File.read(filename)
      content

    end

    def ensure_valid_order(order)
      if (order != :old && order != :new)
        raise StandardError
      end
    end

    def prepare
      if (defined? @tmp_path)
        system(" rm -rf #{@tmp_path} ")
        FileUtils::mkdir_p "#{@tmp_path}"
      end
    end

    def valid_date?(data)
      !data.sunday? && !data.saturday?
    end

    def parse_date(date)
      year = date.year.to_s;
      day = sprintf('%02.f', date.mday)
      month = sprintf('%02.f', date.month)

      return day, month, year
    end


    def get_next_date_same_order(date)
      delta = (@order == :old ? -1 : 1)

      date += delta

      date += delta until valid_date?(date)

      date = nil if (date > Date.today)

      date
    end

    def get_next_date(data)
      return Date.today if data.nil?

      next_date = get_next_date_same_order(data)

      if (next_date.nil?)

        @order = :old
        puts 'Order changed: Fetching old ones.'

        data = get_last_date

        return (data.nil? ? Date.today : get_next_date_same_order(data))
      end

      next_date
    end

    def get_last_date
      conexao = PgSQL.new
      data = conexao.get_last_date(self.get_journal_id, @order)
      conexao.destroy

      data
    end

    def start
      connection = PgSQL.new

      ensure_valid_order(@order)

      date = get_last_date
      connection.destroy

      while (date > get_initial_date)
        date = get_next_date(date)

        if (date > get_initial_date)
          day, month, year = parse_date(date)

          print " * #{year}-#{month}-#{day} "
          fetch_date(day, month, year, "#{year}-#{month}-#{day}")
        end
      end

      puts "Finished journal id #{get_journal_id}"
    end

  end
end
