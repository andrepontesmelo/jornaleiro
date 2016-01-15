require_relative '../../jornais/jornal_dou'

module Jornaleiro

  RSpec.describe '.JornalDOU' do
    let(:j) { JornalDOU.new }

    it "deve saltar sábados e domingos" do
      sexta = Date.parse('2016-01-01')

      expect(j.obtem_proxima_data(sexta).strftime('%A')).to eq('Monday')
    end
  end
end

