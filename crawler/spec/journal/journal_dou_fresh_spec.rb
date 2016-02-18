require 'spec_helper'

# Test for contry level journal
module Jornaleiro
  RSpec.describe '.JournalDOUFresh' do
    let(:j) { JournalDOUFresh.new }

    it 'should skip saturdays and sundays' do
      friday = Date.parse('2016-01-01')

      expect(j.next_date(friday).strftime('%A')).to eq('Monday')
    end
  end
end
