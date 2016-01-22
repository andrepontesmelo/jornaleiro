require_relative 'journal/journal_bh'
require_relative 'journal/journal_mg'
require_relative 'journal/journal_dou_proxy'

module Jornaleiro
  class Crawler

    attr_accessor :journals

    def initialize
      @journals = [Jornaleiro::JournalDOUProxy.new, JournalBH.new, Jornaleiro::JournalMG.new]
    end

    def start
      @journals.each {| j|
        j.start
      }
    end
  end


  Crawler.new.start
end





