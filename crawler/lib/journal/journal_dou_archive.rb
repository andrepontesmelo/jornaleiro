require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative 'journal_dou'
require_relative '../capybara_util'

module Jornaleiro
  # Crawler script for old DOU public journals
  class JournalDOUArchive < JournalDOU
    include Capybara::DSL

    DOU_BACKUP_PATH = '/home/douarchive'.freeze
    LONG_SLEEP = 10

    @pgsql = nil
    @d = nil

    def initialize
      super()

      @tmp_path = '/tmp/douarchive'
      @pgsql = PgSQL.new
    end

    def prepare
      super

      CapybaraUtil.new.use_poltergeist
      Capybara.app_host = Capybara.default_host = 'http://www.jusbrasil.com.br'
    end

    def main_session_id
      return 5 if session_name == 'Seção 1' || session_name == 'SECAO'
      return 6 if session_name == 'Seção 2'
      return 7 if session_name == 'Seção 3'
      return 8 if session_name == 'Edição extra - Seção 1'

      nil
    end

    def secundary_session_id
      return 9 if session_name == 'Suplemento - Seção 1'
      return 15 if session_name == 'Edição extra - Seção 2'
      return 16 if session_name.split(' ').first == 'Decreto'
      return 17 if session_name == 'Edição extra - Seção 3'

      nil
    end

    def session_id(session_name)
      return main_session_id unless main_session_id.nil
      return secundary_session_id unless secundary_session_id.nil

      raise Exception, "Session not expected: #{session_name}"
    end

    def initial_date
      Date.parse('1900-01-01')
    end

    def max_date
      Date.parse('2015-03-01')
    end

    def next_page
      within first('.small-paginator') do
        all('a').last.click
      end
    end

    def fetch(day, month, year, date)
      @d = {}
      @d[:sessions_completed] = []
      @d[:content] = {}
      @d[:errs] = 0
      @d[:last_url] = ''
      @d[:last_page] = 0
      @d[:session_count] = 1
      @d[:session] = 0
      @d[:session_id] = 0

      download(day, month, year, date)
    end

    def hash(session_id, page)
      "#{session_id}_#{page}"
    end

    def save_page(session_id, page, date)
      content_hash = @d[:content]
      content = content_hash[hash(session_id, page)]
      @pgsql.insert_document(date, page, session_id,
                             content[:content], nil, content[:url])
      print '.'
    end

    def save(date)
      @d[:sessions_completed].each do |session|
        session.each do |session_id, page_count|
          puts "\nSession #{session_id} has #{page_count} pages."

          [1..page_count].each do |p|
            save_page session_id, p, date
          end
        end
      end
    end

    def set_content(page, url, content)
      content_hash[hash(@d[:session_id], page)] =
        {
          url: url,
          content: content
        }
    end

    def print_status(p, content, date, page_count)
      puts "Page #{p} has content: #{content}. "
      puts " #{date} | Page #{p}/#{page_count} @ session id " \
      "#{@d[:session_id]} [#{@d[:session] + 1}/" \
      "#{@d[:session_count]}] | #{@d[:errs]} errors "
    end

    def download_page(p, date, page_count)
      within('#wrap') do
        content = find('.text').text
        print_status p, content, date, page_count

        set_content p, page.current_url, content
        next_page unless p == page_count

        @d[:last_url] = page.current_url
        @d[:last_page] = p
      end
    end

    def click_first_pagination
      within('.diario-pages') do
        pages = all('.page')
        pages[0].click
      end
    end

    def not_finished_day
      @d[:session] < @d[:session_count]
    end

    def requires_resume
      required = @d[:errs] != 0 && @d[:last_url] != ''
      puts "Resume require for last url #{@d[:last_url]}" if required

      required
    end

    def download_all_pages(date)
      page_count = first('.pages').text.split(' ').last.to_i

      [(@d[:last_page] + 1)..page_count].each do |p|
        download_page p, date, page_count
      end

      page_count
    end

    def load_error
      title = all('.title')[2]
      return true if !title.nil? && title.text == 'Ops! O que aconteceu?'

      false
    end

    def finish_session(page_count)
      puts "End of session #{@d[:session_id]}"
      @d[:errs] = 0
      @d[:session] += 1
      @d[:last_page] = 0
      new_hash = { @d[:session_id] => page_count }
      @d[:sessions_completed].push(new_hash)
    end

    def report_error(e)
      puts "Timeout error. : #{e} #{e.message}"
      @d[:errs] += 1
      sleep 3
    end

    def sessions
      all(:xpath, '//h3/a')
    end

    def click_next_session
      sessions[@d[:session]].click
    end

    def visit_page(day, month, year)
      formatted_month = format '%02d', month
      visit "/diarios/DOU/#{year}/#{formatted_month}/#{day}"
      return nil if load_error
    end

    def parse_session
      @d[:session_id] = session_id(sessions[@d[:session]].text)
      puts "Session: #{sessions[@d[:session]].text} " \
      "| Session Id: #{@d[:session_id]}"
    end

    def sync_d
      @d[:session_count] = sessions.count
      @d[:session] = @downloading[:sessions_completed].count
      puts "Session current = #{@d[:session] + 1}"
      raise ArgumentError, 'Session' if sessions[@d[:session]].nil?

      parse_session
    end

    def visit_day(day, month, year)
      page.reset_session!
      if requires_resume
        visit @d[:last_url]
      else
        visit_page day, month, year
        sync_d
        click_next_session
        click_first_pagination
      end
    end

    def download(day, month, year, date)
      while not_finished_day
        visit_day(day, month, year)
        page_count = download_all_pages date
        finish_session page_count
      end
      true

    rescue Capybara::Poltergeist::TimeoutError, NoMethodError => e
      report_error e
      retry
    end

    def fetch_date(day, month, year, date)
      prepare
      puts 'fetch_date DOU Archieve'
      downloaded = fetch(day, month, year, date)
      save date unless downloaded.nil?

    rescue Capybara::ElementNotFound => e
      puts "Skipping date #{date} due to unrecovery error #{e}."
    end
  end
end
