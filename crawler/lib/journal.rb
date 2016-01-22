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
        puts "#{order.to_s} is not a valid order."
        raise StandardError
      end
    end

    def get_initial_date
      nil
    end

    def get_last_date
      nil
    end

    def within_range?(date)
      date_accepted = true

      date = Date.parse(date)  #if date.class == String.class

      date_accepted &= date >= get_initial_date unless get_initial_date.nil?
      date_accepted &= date <= get_max_date unless get_max_date.nil?

      date_accepted
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

    def get_max_date
      nil
    end


    def start
      connection = PgSQL.new

      ensure_valid_order(@order)

      date = get_last_date
      connection.destroy

      date = get_next_date(date)

      while (get_initial_date.nil? || date > get_initial_date)

        if (get_initial_date.nil? || date > get_initial_date)
          day, month, year = parse_date(date)

          print " * #{year}-#{month}-#{day} "
          fetch_date(day, month, year, "#{year}-#{month}-#{day}")
        end

        date = get_next_date(date)
      end

      puts "Finished journal id #{get_journal_id}"
    end

  end
end
