require 'date'

module Jornaleiro
  # Base class for crawler journals.
  class Journal
    attr_accessor :order

    def initialize
      @order = :new
    end

    def find_file(session, session_id, page)
      session = 'Edição-Extra' if session_id == 8
      files = Dir["#{@tmp_path}/#{session}*.cleaned"]

      return files[0] if files.size == 1
      files = Dir["#{@tmp_path}/#{session}* #{page}.pdf.transcribed.cleaned"]
      return files[0] if files.size == 1

      puts "Not implemented: Dir #{@tmp_path}/#{session}* " \
      "#{page}.pdf.transcribed.cleaned"
      puts "Returned #{files.size} files."
      exit 1
    end

    def read_file(session, session_id, page)
      filename = find_file(session, session_id, page)
      content = File.read(filename)
      content
    end

    def ensure_valid_order(order)
      if order != :old && order != :new
        raise StandardError, "#{order} is not a valid order."
      end
    end

    def initial_date
      nil
    end

    def max_date
      nil
    end

    def within_range?(date)
      date_accepted = true

      date = Date.parse(date) unless date.class == Date.new.class

      date_accepted &= date >= initial_date unless initial_date.nil?
      date_accepted &= date <= max_date unless max_date.nil?

      date_accepted
    end

    def prepare
      if defined? @tmp_path
        system(" rm -rf #{@tmp_path} ")
        FileUtils.mkdir_p(@tmp_path.to_s)
      end
    end

    def valid_date?(date)
      !date.sunday? && !date.saturday?
    end

    def parse_date(date)
      year = date.year.to_s
      day = format('%02.f', date.mday)
      month = format('%02.f', date.month)

      return day, month, year
    end

    def next_date_same_order(date)
      delta = (@order == :old ? -1 : 1)

      date += delta

      date += delta until valid_date?(date)

      date = nil if date > Date.today

      date
    end

    def next_date(date)
      return Date.today if date.nil?

      next_date = next_date_same_order(date)

      if next_date.nil? && @order == :new
        @order = :old
        puts 'Order changed: Fetching old ones.'

        date = last_fetched_date

        next_date = (date.nil? ? Date.today : next_date_same_order(date))
      end

      return next_date if within_range?(next_date)

      nil
    end

    def last_fetched_date
      connection = PgSQL.new
      date = connection.last_fetched_date(journal_id, @order)
      connection.destroy

      date
    end

    def should_fetch(date)
      return false if date.nil?
      return true if last_fetched_date.nil?
      within_range?(date)
    end

    def start
      date = next_date(last_fetched_date)

      while should_fetch(date)
        day, month, year = parse_date(date)

        print " * #{year}-#{month}-#{day} "
        fetch_date(day, month, year, "#{year}-#{month}-#{day}")
        date = next_date(date)
      end

      puts "Finished journal id #{journal_id}"
    end
  end
end
