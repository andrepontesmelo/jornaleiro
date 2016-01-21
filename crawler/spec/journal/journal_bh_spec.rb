require 'spec_helper'

module Jornaleiro

  RSpec.describe '.JournalBH' do

    let(:j) { JournalBH.new }
    it "should skip sundays and mondays" do
      saturday = Date.parse('2016-01-02')

      expect(j.get_next_date(saturday).strftime('%A')).to eq('Tuesday')
    end

    it "should not skip saturdays" do
      friday = Date.parse('2016-01-01')

      expect(j.get_next_date(friday).strftime('%A')).to eq('Saturday')
    end
  end
end

