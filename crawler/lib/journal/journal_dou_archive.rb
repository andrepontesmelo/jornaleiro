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

      CapybaraUtil.new.set_driver :selenium
      Capybara.app_host = Capybara.default_host = 'http://pesquisa.in.gov.br'

    end

    def fetch(day, month, year, date)
      for p in 1..10

        visit "/imprensa/servlet/INPDFViewer?jornal=1&pagina=#{"%02d" % p}&data=#{day}/#{"%02d" % month}/#{year}&captchafield=firistAccess"
        puts find('#viewer').text
      end
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

