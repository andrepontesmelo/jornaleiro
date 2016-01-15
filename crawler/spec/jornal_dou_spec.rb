require_relative 'jornais/jornal_dou_spec'

module Jornaleiro

  RSpec.describe '.JornalDOU' do
    let(:j) { JornalDOU.new }

    it "deve interpretar um nome de arquivo pdf" do
      interpretado = j.interpreta_arquivo 'DO3_2016_01_12.pdf?arg1=1eRJ1syC0L4DNPgeptgr7g&arg2=1452826103'
      expect(interpretado[:ano]).to eq '2016'
      expect(interpretado[:mes]).to eq '01'
      expect(interpretado[:dia]).to eq '12'
      expect(interpretado[:pagina]).to be_nil
      expect(interpretado[:sessao]).to eq 7
    end

    it "deve interpretar um nome de arquivo TRF" do
      interpretado = j.interpreta_arquivo 'EDJF1_2015_03_19.pdf'

      expect(interpretado[:ano]).to eq '2015'
      expect(interpretado[:mes]).to eq '03'
      expect(interpretado[:dia]).to eq '19'
      expect(interpretado[:pagina]).to be_nil
      expect(interpretado[:sessao]).to eq 14
    end


    it "deve limpar o nome de arquivo pdf" do
      limpo = j.limpar_arquivo('DO1_2016_01_11_SuplementoAnvisa.pdf?arg1=dADPwykoU_69iNuprCpnhA&arg2=1452823633')

      expect(limpo).to eq 'DO1_2016_01_11_SuplementoAnvisa.pdf'
    end
  end
end

