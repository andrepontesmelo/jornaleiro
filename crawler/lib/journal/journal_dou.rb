require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative '../journal'
require_relative '../capybara_util'

module Jornaleiro
  # Base class for DOU crawlers
  class JournalDOU < Journal
    def journal_id
      3
    end

    def valid_date(date)
      !date.sunday? && !date.saturday?
    end
  end
end
