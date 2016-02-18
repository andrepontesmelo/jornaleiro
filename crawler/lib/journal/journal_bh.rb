require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require 'json'
require_relative '../pg_sql'
require_relative '../capybara_util'
require_relative '../journal'

module Jornaleiro
  # Crawler for  Belo Horizonte municipal public journal
  class JournalBH < Journal
    include Capybara::DSL
    HOST = 'http://portal6.pbh.gov.br'.freeze
    SESSION_ID = 4

    def initialize
      super
    end

    def valid_date?(date)
      !date.sunday? && !date.monday?
    end

    def initial_date
      Date.parse('1996-02-17')
    end

    def journal_id
      2
    end

    def prepare
      super

      CapybaraUtil.new.use_poltergeist
      Capybara.app_host = Capybara.default_host = HOST

      puts 'fetch_date BH'
    end

    def save_articles(articles, date)
      pgsql = PgSQL.new

      articles.each_with_index do |article, page|
        pgsql.insert_document(date, page, SESSION_ID, article[:content],
                              article[:title], article[:url])
      end

      pgsql.destroy
    end

    def fetch_date(day, month, year, date)
      prepare

      articles = fetch_articles(day, month, year)

      save_articles(articles, date) unless articles.nil?
    end

    def fetch_hrefs(day, month, year)
      page.reset_session!

      visit "/dom/iniciaEdicao.do?method=DomDia&day=#{day}" \
            "/#{month}/#{year}&comboAno=#{year}"

      find('#imgExtRecTodos').click

      hrefs = all(:css, '.ChamadaArtigo')

      puts " * #{hrefs.length}"

      hrefs
    rescue Capybara::ElementNotFound, Capybara::Poltergeist::TimeoutError,
           Capybara::Poltergeist::DeadClient => e
      puts e.message
    end

    def fetch_article(href)
      article = {}

      article[:title] = href.text
      article[:url] = href.first('a')['href']
      print '.'

      within_window open_new_window do
        visit article[:url]

        article[:content] = all('#esquerda')[1].text
        page.execute_script 'window.close();'
      end

      article
    end

    def fetch_articles(day, month, year)
      articles = []
      hrefs = fetch_hrefs(day, month, year)

      hrefs.each do |href|
        articles.push fetch_article(href)
      end

      puts ''

      articles
    rescue Capybara::ElementNotFound, Capybara::Poltergeist::TimeoutError,
           Capybara::Poltergeist::DeadClient => e
      puts e.message
    end
  end
end
