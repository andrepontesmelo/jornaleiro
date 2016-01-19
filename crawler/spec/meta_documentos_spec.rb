require 'spec_helper'

module Jornaleiro

  RSpec.describe ".MetaDocumentos" do

    let(:m) { MetaDocumentos.new }

    it "deve permitir inicialização da sessão" do
      documento = MetaDocumento.new(2)
      expect(documento.sessao_id).to be(2)
    end

    it "deve adicionar documento" do
      documento = MetaDocumento.new(0)
      m.adiciona(documento)

      expect(m.documentos.count).to be 1
    end

    it "deve entrar uma sessão na lista de documentos" do
      documento = MetaDocumento.new(55)
      m.adiciona(documento)

      expect(m.contem_sessao(55)).to be_truthy
    end

    it "deve encontrar um meta documento pelo número da sessão" do
      documento = MetaDocumento.new(55)
      m.adiciona(documento)

      expect(m.obtem_documento(55)).to eq(documento)
    end

    it "deve transformar obter lista " do
      expect(m.obtem_lista.class).to be(Array.new.class)
    end

    it "deve transformar obter lista com os elementos adicionados " do
      documento = MetaDocumento.new(0)

      m.adiciona(documento)
      expect(m.obtem_lista[0]).to eq(documento)
    end

    it "não deve permitir adição de dois documentos de mesma sessão" do
      m.adiciona(MetaDocumento.new(0))
      expect { m.adiciona(MetaDocumento.new(0)) }.to raise_error(ArgumentError)
    end

    it "deve permitir adição de dois documentos sessões diferentes" do
      m.adiciona(MetaDocumento.new(1))
      expect { m.adiciona(MetaDocumento.new(2)) }.to_not raise_error
    end

    it "deve obter lista de pdfs unificados " do
      documento1 = MetaDocumento.new(1)
      documento1.lista_pdfs.push('pdf1', 'pdf2')

      documento2 = MetaDocumento.new(2)
      documento2.lista_pdfs.push('pdf3', 'pdf4')

      m.adiciona(documento1)
      m.adiciona(documento2)

      expect(m.obtem_lista_pdfs).to eq(['pdf1', 'pdf2', 'pdf3', 'pdf4'])
    end
  end
end

