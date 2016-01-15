require_relative '../../jornais/jornal_bh'

module Jornaleiro

  RSpec.describe '.JornalBH' do

    let(:j) { JornalBH.new }
    it "deve saltar domingos e segunda" do
      sabado = Date.parse('2016-01-02')

      expect(j.obtem_proxima_data(sabado).strftime('%A')).to eq('Tuesday')
    end

    it "não deve saltar sábados" do
      sexta = Date.parse('2016-01-01')

      expect(j.obtem_proxima_data(sexta).strftime('%A')).to eq('Saturday')
    end
  end
end

