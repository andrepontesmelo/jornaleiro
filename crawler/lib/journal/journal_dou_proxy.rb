require 'capybara/poltergeist'
require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require_relative '../pg_sql'
require_relative '../journal'
require_relative '../journal/journal_dou_archive'
require_relative '../journal/journal_dou_fresh'
require_relative '../capybara_util'

module Jornaleiro
  # Forward requests to either fresh or archive DOU crawlers.
  class JournalDOUProxy < JournalDOU
    include Capybara::DSL

    @archive = nil
    @fresh = nil

    def initialize
      super

      @fresh = JournalDOUFresh.new
      @archive = JournalDOUArchive.new
    end

    def fetch_date(day, month, year, date)
      selected = nil

      selected = @archive if @archive.within_range?(date)
      selected = @fresh if @fresh.within_range?(date)

      puts "Date: #{date}"
      raise StandardError if selected.nil?

      selected.fetch_date(day, month, year, date)
    end
  end
end
