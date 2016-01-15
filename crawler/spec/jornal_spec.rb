require_relative '../jornal'

module Jornaleiro

  RSpec.describe ".Jornal" do

    let(:j) { Jornal.new }

    it "valida ordem" do
      expect { j.valida_ordem(:recentes) }.not_to raise_error
      expect { j.valida_ordem(:antigos) }.not_to raise_error
      expect { j.valida_ordem(:invalido) }.to raise_error(StandardError)
    end


    it "deve pegar os recentes primeiro." do
      expect(j.ordem).to equal(:recentes)
    end

    it "deve obter o dia de hoje caso banco de dados esteja vazio" do
      expect(j.obtem_proxima_data(nil).to_s).to eq(Date.today.to_s)

    end

    it "deve obter o dia seguinte sem saltos" do
      segunda_feira = Date.parse('2016-01-04')

      expect(j.obtem_proxima_data(segunda_feira).to_s).to eq('2016-01-05')
    end

    it "deve saltar sábidos e domingos" do
      sexta_feira = Date.parse('2016-01-01')

      expect(j.obtem_proxima_data(sexta_feira).strftime('%A')).to eq('Monday')
    end


    it "deve passar a pegar jornais antigos assim que pegar todos os recentes" do

      ultima_data_obtida = '2014-01-02'
      data_anterior_ultima_obtida = '2014-01-01'

      Jornaleiro::Jornal.any_instance.stub(obtem_ultima_data: Date.parse(ultima_data_obtida))

      expect(j.obtem_proxima_data(Date.today).to_s).to eq(data_anterior_ultima_obtida)

    end

  end
end

