require 'spec_helper'

module Jornaleiro

  RSpec.describe '.JournalDOU' do
    let(:j) { JornalDOUFresh.new }

    it "should skip saturdays and sundays" do
      friday = Date.parse('2016-01-01')

      expect(j.get_next_date(friday).strftime('%A')).to eq('Monday')
    end
  end
end

