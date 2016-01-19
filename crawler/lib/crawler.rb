require_relative 'jornais/jornal_bh'
require_relative 'jornais/jornal_mg'
require_relative 'jornais/jornal_dou'

module Jornaleiro
  class Crawler
    def initialize
      Jornaleiro::JornalDOU.new.inicia();
      Jornaleiro::JornalMG.new.inicia();
      JornalBH.new.inicia()
    end
  end

  Crawler.new

end





