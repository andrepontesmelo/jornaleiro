require 'spec_helper'

# Test for state level journal MG
module Jornaleiro
  RSpec.describe '.JournalMG' do
    let(:j) { JournalMG.new }

    it 'should skip sunday and monday' do
      saturday = Date.parse('2016-01-02')

      expect(j.next_date(saturday).strftime('%A')).to eq('Tuesday')
    end

    it 'should not skip saturdays' do
      friday = Date.parse('2016-01-01')

      expect(j.next_date(friday).strftime('%A')).to eq('Saturday')
    end
  end
end
