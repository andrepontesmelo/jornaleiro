require 'spec_helper'

# Tests for jornaleiro module
module Jornaleiro
  RSpec.describe '.DateHelper' do
    let(:d) { DateHelper.new }

    it 'should get the first month name' do
      expect(d.month_name(1)).to eq('Janeiro')
    end

    it 'should get the last month name' do
      expect(d.month_name(12)).to eq('Dezembro')
    end
  end
end
