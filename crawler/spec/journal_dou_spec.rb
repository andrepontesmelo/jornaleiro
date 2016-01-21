require 'spec_helper'

module Jornaleiro

  RSpec.describe '.JornalDOU' do
    let(:j) { JornalDOU.new }

    it "should parse a pdf file name" do
      parsed = j.parse_file 'DO3_2016_01_12.pdf?arg1=1eRJ1syC0L4DNPgeptgr7g&arg2=1452826103'
      expect(parsed[:year]).to eq '2016'
      expect(parsed[:month]).to eq '01'
      expect(parsed[:day]).to eq '12'
      expect(parsed[:page]).to be_nil
      expect(parsed[:session]).to eq 7
    end

    it "should parse a TRF (Tribunal Regional Federal) file name" do
      parsed = j.parse_file 'EDJF1_2015_03_19.pdf'

      expect(parsed[:year]).to eq '2015'
      expect(parsed[:month]).to eq '03'
      expect(parsed[:day]).to eq '19'
      expect(parsed[:page]).to be_nil
      expect(parsed[:session]).to eq 14
    end


    it "should clean the pdf file name" do
      cleaned = j.clean_file_name('DO1_2016_01_11_SuplementoAnvisa.pdf?arg1=dADPwykoU_69iNuprCpnhA&arg2=1452823633')

      expect(cleaned).to eq 'DO1_2016_01_11_SuplementoAnvisa.pdf'
    end
  end
end

