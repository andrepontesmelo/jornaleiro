require_relative 'journal/journal_bh'
require_relative 'journal/journal_mg'
require_relative 'journal/journal_dou_proxy'

# Main module
module Jornaleiro
  #  Trigger crawlers
  class Crawler
    attr_accessor :journals

    def initialize
      @journals = [ #JournalDOUProxy.new,
                    JournalMG.new,
                   JournalBH.new]
    end

    def start
      @journals.each(&:start)
    end
  end

  Crawler.new.start
end
