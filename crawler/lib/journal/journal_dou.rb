require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative '../journal'
require_relative '../capybara_util'

module Jornaleiro
  class JournalDOU < Journal
    def get_journal_id
      3
    end

    def valid_date(data)
      return (!data.sunday? && !data.saturday?)
    end
  end
end
