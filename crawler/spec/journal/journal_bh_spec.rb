require 'spec_helper'

# Tests for city level
module Jornaleiro
  RSpec.describe '.JournalBH' do
    let(:j) { JournalBH.new }
    it 'should skip sundays and mondays' do
      saturday = Date.parse('2016-01-02')

      expect(j.next_date(saturday).strftime('%A')).to eq('Tuesday')
    end

    it 'should not skip saturdays' do
      friday = Date.parse('2016-01-01')

      expect(j.next_date(friday).strftime('%A')).to eq('Saturday')
    end
  end
end
