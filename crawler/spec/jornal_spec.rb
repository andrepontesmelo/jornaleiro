require 'spec_helper'

module Jornaleiro

  RSpec.describe ".Journal" do

    let(:j) { Journal.new }

    it "should validate order" do
      expect { j.ensure_valid_order(:new) }.not_to raise_error
      expect { j.ensure_valid_order(:old) }.not_to raise_error
      expect { j.ensure_valid_order(:invalid) }.to raise_error(StandardError)
    end

    it "should fetch new documents first" do
      expect(j.order).to equal(:new)
    end

    it "should fetch today's public journal in case of empty database" do
      expect(j.get_next_date(nil).to_s).to eq(Date.today.to_s)
    end

    it "should fetch next day in general" do
      monday = Date.parse('2016-01-04')

      expect(j.get_next_date(monday).to_s).to eq('2016-01-05')
    end

    it "should skip saturdays and sundays" do
      friday = Date.parse('2016-01-01')

      expect(j.get_next_date(friday).strftime('%A')).to eq('Monday')
    end


    it "should grab old journals after grabbing all of the most recent ones" do
      last_grabbed_date = '2014-01-02'
      day_before_last_grabbed = '2014-01-01'

      allow_any_instance_of(Journal).to receive(:get_last_date).and_return(Date.parse(last_grabbed_date))

      expect(j.get_next_date(Date.today).to_s).to eq(day_before_last_grabbed)
    end

    it "should accept any date without date constrains" do
      last_grabbed_date = '2014-01-02'
      allow_any_instance_of(Journal).to receive(:get_last_date).and_return(Date.parse(last_grabbed_date))
      expect(j.within_range?(Date.parse('2001-01-01'))).to be_truthy
    end

    it "should constraint initial date" do
      allow_any_instance_of(Journal).to receive(:get_last_date).and_return(Date.parse('2014-01-02'))

      allow_any_instance_of(Journal).to receive(:get_initial_date).and_return(Date.parse('2001-01-02'))

      expect(j.within_range?(Date.parse('2001-01-01'))).to be_falsey
    end

    it "should accept a valid date with initial date constraint" do
      allow_any_instance_of(Journal).to receive(:get_last_date).and_return(Date.parse('2014-01-02'))

      allow_any_instance_of(Journal).to receive(:get_initial_date).and_return(Date.parse('2001-01-02'))

      expect(j.within_range?(Date.parse('2001-01-03'))).to be_truthy
    end

    it "should accept a valid date with max date constraint" do
      allow_any_instance_of(Journal).to receive(:get_last_date).and_return(Date.parse('2014-01-02'))

      allow_any_instance_of(Journal).to receive(:get_last_date).and_return(Date.parse('2001-01-02'))

      expect(j.within_range?(Date.parse('2001-01-01'))).to be_truthy
    end

    it "should constraint max date" do
      allow_any_instance_of(Journal).to receive(:get_last_date).and_return(Date.parse('2014-01-02'))

      allow_any_instance_of(Journal).to receive(:get_last_date).and_return(Date.parse('2001-01-02'))

      expect(j.within_range?(Date.parse('2001-01-03'))).to be_falsey
    end

    it "should accept a valid date with initial and max date constraint" do
      allow_any_instance_of(Journal).to receive(:get_last_date).and_return(Date.parse('2014-01-02'))

      allow_any_instance_of(Journal).to receive(:get_initial_date).and_return(Date.parse('2001-01-01'))
      allow_any_instance_of(Journal).to receive(:get_last_date).and_return(Date.parse('2001-01-02'))

      expect(j.within_range?(Date.parse('2001-01-01'))).to be_truthy
      expect(j.within_range?(Date.parse('2001-01-02'))).to be_truthy
    end
  end
end