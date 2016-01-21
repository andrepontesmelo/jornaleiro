require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require 'json'
require_relative '../pg_sql'
require_relative '../capybara_util'
require_relative '../journal'

module Jornaleiro

  class JournalBH < Journal

    include Capybara::DSL
    HOST = 'http://portal6.pbh.gov.br'
    SESSION_ID = 4

    def initialize
      super()

    end

    def valid_date?(data)
      !data.sunday? && !data.monday?
    end

    def get_initial_date
      return Date.parse('1996-01-04')
    end

    def get_journal_id
      2
    end

    def prepare
      super

      CapybaraUtil.new.set_driver(:poltergeist)
      Capybara.app_host = Capybara.default_host = HOST
    end

    def fetch_date(day, month, ano, data)

      prepare
      puts "fetch_date BH"

      articles = fetch_articles(day, month, ano)

      if (!articles.nil?)
        pgsql = PgSQL.new

        articles.each_with_index { |article, page|
          pgsql.insert_document(data, page, SESSION_ID , article[:content], article[:title], article[:url])
        }

        pgsql.destroy()
      end
    end


    def fetch_articles(day, month, year)

      articles = Array.new();

      page.reset_session!

      visit("/dom/iniciaEdicao.do?method=DomDia&day=#{day}/#{month}/#{year}&comboAno=#{year}")

      puts "#{HOST}/dom/iniciaEdicao.do?method=DomDia&day=#{day}/#{month}/#{year}&comboAno=#{year}"


      find('#imgExtRecTodos').click

      hrefs = all(:css, ".ChamadaArtigo")

      puts " * #{hrefs.length} "

      hrefs.each { |href|

        article = {}

        article[:title] = href.text

        print '.'

        url = href.first("a")["href"]

        within_window open_new_window do
          visit(url)

          article[:content] = all("#esquerda")[1].text
          article[:url] = url
          page.execute_script "window.close();"
        end

        articles.push(article);
      }

      puts ""

      articles

    rescue Capybara::ElementNotFound, Capybara::Poltergeist::TimeoutError, Capybara::Poltergeist::DeadClient => e
      puts e.message
    end

  end

end




