require_relative 'journal/journal_bh'
require_relative 'journal/journal_mg'
require_relative 'journal/journal_dou_fresh'

# Main module
module Jornaleiro
  #  Trigger crawlers
  class Crawler
    attr_accessor :journals

    def initialize
      @journals = [JournalMG.new,
                   JournalBH.new,
                   JournalDOUFresh.new]
    end

    def start
      @journals.each(&:start)
    end
  end

  Crawler.new.start
end
