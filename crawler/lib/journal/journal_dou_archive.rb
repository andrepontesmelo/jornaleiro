require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative 'journal_dou'
require_relative '../capybara_util'

module Jornaleiro

  class JournalDOUArchive < JournalDOU
    include Capybara::DSL

    DOU_BACKUP_PATH = "/home/douarchive"
    LONG_SLEEP = 10

    @pgsql = nil
    @downloading = nil

    def initialize
      super()

      @tmp_path = "/tmp/douarchive"
      @pgsql = PgSQL.new
    end

    def prepare
      super

      CapybaraUtil.new.set_driver :poltergeist
      Capybara.app_host = Capybara.default_host = 'http://www.jusbrasil.com.br'
    end

    def get_session_id(session_name)
      case session_name
        when 'Seção 1'
          5
        when 'Seção 2'
          6
        when 'Seção 3'
          7
        when 'Edição extra - Seção 1'
          8
        when 'Suplemento - Seção 1'
          9
        when 'Edição extra - Seção 2'
          15
        else
          if (session_name.split(' ').first == 'Decreto')
             return 16
          else
            puts session_name
            raise Exception.new("Session not expected: #{session_name}")
          end
      end
    end

    def get_initial_date
      Date.parse('1900-01-01')
    end

    def get_max_date
      Date.parse('2015-03-01')
    end

    def next_page
      within first('.small-paginator') do
        all('a').last.click
      end
    end

    def fetch(day, month, year, date)
      @downloading = {}
      @downloading[:sessions_completed] = []
      @downloading[:content] = {}
      @downloading[:failures] = 0
      @downloading[:last_url] = ''
      @downloading[:last_page] = 0
      @downloading[:session_count] = 1
      @downloading[:session_current] = 0
      @downloading[:current_session_id] = 0

      download(day, month, year, date);
    end

    def hash(session_id, page)
      "#{session_id}_#{page}"
    end

    def save(date)
      content_hash = @downloading[:content]

      @downloading[:sessions_completed].each { |session|
        session.each do |session_id, page_count|
          puts "\nSession #{session_id} has #{page_count} pages."

          for p in 1..page_count do

             # def insert_ocumendate, page, session_id, content, title = nil, url = nil)
            content = content_hash[hash(session_id, p)];
            @pgsql.insert_document(date, p, session_id, content[:content], nil, content[:url])
            print '.'
          end

        end
      }

    end

    def download(day, month, year, date)

      sessions_completed_list = @downloading[:sessions_completed]
      content_hash = @downloading[:content]



      page.reset_session!

      while (@downloading[:session_current] < @downloading[:session_count]) do

        if @downloading[:failures] == 0 || @downloading[:last_url] == ''
          visit "/diarios/DOU/#{year}/#{"%02d" % month}/#{day}"

          title = all('.title')[2]

          if !title.nil? && (title.text == 'Ops! O que aconteceu?')
            return nil;
          end

          within('#wrap') do
            sessions = all(:xpath, '//h3/a');
            @downloading[:session_count] = sessions.count

            puts "Sessions_completed_list.count = #{sessions_completed_list.count}"
            @downloading[:session_current] = sessions_completed_list.count
            puts "Session current = #{@downloading[:session_current] + 1}"

            if (sessions[@downloading[:session_current]].nil?)
              raise Exception.new("Session is nil")
            end

            @downloading[:current_session_id] = get_session_id(sessions[@downloading[:session_current]].text)
            puts "Session: #{sessions[@downloading[:session_current]].text} | Session Id: #{@downloading[:current_session_id]}"

            sessions[@downloading[:session_current]].click
          end

          within('.diario-pages') do
            pages = all('.page')
            pages[0].click
          end

        else
          puts "Visiting last url #{@downloading[:last_url]}"
          visit @downloading[:last_url]
        end

        page_count = first('.pages').text.split(' ').last.to_i
#        page_count = 2

        for p in (@downloading[:last_page] + 1)..page_count
          content = find('.text').text;
          puts "Page #{p} has content: #{content}. "
          puts " #{date} | Page #{p}/#{page_count} @ session id #{@downloading[:current_session_id]} [#{@downloading[:session_current] + 1}/#{@downloading[:session_count]}] | #{@downloading[:failures]} failures "

          #sleep @downloading[:failures]

          content_hash[hash(@downloading[:current_session_id], p)] =
              {
                  :url => page.current_url,
                  :content => content
              }

          next_page unless (p == page_count)

          @downloading[:last_url] = page.current_url
          @downloading[:last_page] = p
        end

        puts "End of session #{@downloading[:current_session_id]}"
        @downloading[:failures] = 0
        @downloading[:session_current] += 1
        @downloading[:last_page] = 0
        sessions_completed_list.push( { @downloading[:current_session_id] => page_count } )
      end

      true

    rescue Capybara::Poltergeist::TimeoutError, NoMethodError => e
      puts "Timeout error. : #{e} #{e.message}"
      @downloading[:failures] += 1
      sleep 3
      retry
    end

    def fetch_date(day, month, year, date)
      prepare
      puts "fetch_date DOU Archieve"
      downloaded = fetch(day, month, year, date)

      save(date) unless downloaded.nil?

    rescue Capybara::ElementNotFound => e
      puts "Skipping date #{date} due to unrecovery error #{e}."
    end

  end
end

