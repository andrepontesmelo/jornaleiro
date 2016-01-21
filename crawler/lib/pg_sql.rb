require 'sequel'

module Jornaleiro
  class PgSQL
    def initialize
      @database = Sequel.postgres(:user => 'jornaleiro', :password => '', :host => 'localhost', :port => 5432)
    end

    def clean_string(content)
      content = content.gsub("\"", "'")
      content = content.gsub("'", "\\\\'")

      content = content.gsub('--', '-') while (content.include? '--')
      content = content.gsub('..', '.') while (content.include? '..')

      encoding_options = {
          :invalid => :replace, # Replace invalid byte sequences
          :undef => :replace, # Replace anything not defined in ASCII
          :replace => '', # Use a blank for those replacements
          :universal_newline => true # Always break lines with \n
      }

      content.encode!(Encoding.find('UTF-8'), encoding_options)

      content
    end

    def destroy
      @database.disconnect
    end

    def insert_document(date, page, session_id, content, title = nil, url = nil)

      content = clean_string(content)

      if (!title.nil?)
        title = clean_string(title)
      end

      documents = @database[:document] # Create a dataset

      documents.insert(:session => session_id,
                        :page => page,
                        :date => date,
                        :content => content,
                        :title => title,
                        :url => url)

    rescue Exception => e
      puts "#{e} "
      puts "Date: #{date} page: #{page}, session_id: #{session_id} title #{title} url #{url}"
      exit 1;
    end

    def get_last_date(journal_id, order)

      #return Date.parse('2016-01-19');

      minmax = (order == :new ? "max" : "min")

      sql = "select #{minmax}(date) as date from document d join session s on d.session=s.id and s.journal=#{journal_id} "

      date = Date.parse(@database.fetch(sql).first[:date].to_s)

      puts sql
      puts date

      date

    rescue Exception => e
      puts "The database is empty: #{e.message} #{e}"
      return nil;
    end

  end

end
