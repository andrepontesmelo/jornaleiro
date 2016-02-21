require 'sequel'
require 'unicode'

module Jornaleiro
  # PostgreSQL connector.
  class PgSQL
    def initialize
      @database = Sequel.postgres(user: 'jornaleiro', password: '',
                                  host: '127.0.0.1', port: 5432)
    end

    def encoding_options
      {
        invalid: :replace, # Replace invalid byte sequences
        undef: :replace, # Replace anything not defined in ASCII
        replace: '', # Use a blank for those replacements
        universal_newline: true # Always break lines with \n
      }
    end

    def clean_string(content)
      content = content.gsub('\"', '\'')
      content = content.gsub("'", "\\\\'")

      content = content.gsub('--', '-') while content.include? '--'
      content = content.gsub('..', '.') while content.include? '..'

      content.encode!(Encoding.find('UTF-8'), encoding_options)
      Unicode.downcase(content)
    end

    def destroy
      @database.disconnect
    end

    def insert_document(date, page, session_id, content, title = nil, url = nil)
      content = clean_string(content)
      title = clean_string(title) unless title.nil?
      documents = @database[:document] # Create a dataset

      documents.insert(session: session_id, page: page, date: date,
                       content: content, title: title, url: url)

    rescue StandardError => e
      puts "#{e} "
      puts "Date: #{date} page: #{page}, session_id: #{session_id} " \
          "title #{title} url #{url}"
      exit 1
    end

    def last_fetched_date(journal_id, order)
      minmax = (order == :new ? 'max' : 'min')

      sql = "select #{minmax}(date) as date from document d join session s " \
      " on d.session=s.id and s.journal=#{journal_id}"

      date = Date.parse(@database.fetch(sql).first[:date].to_s)

      date
    rescue StandardError => e
      puts "The database is empty: #{e.message} #{e}"
      nil
    end
  end
end
