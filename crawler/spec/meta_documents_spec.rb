require 'spec_helper'

# Tests for meta documents
module Jornaleiro
  RSpec.describe '.MetaDocuments' do
    let(:m) { MetaDocuments.new }

    it 'should set session id @ class constructor' do
      document = MetaDocument.new(2)
      expect(document.session_id).to be(2)
    end

    it 'should push a document do the list of documents' do
      document = MetaDocument.new(0)
      m.push(document)

      expect(m.docs.count).to be 1
    end

    it 'should find an already inserted session' do
      document = MetaDocument.new(55)
      m.push(document)

      expect(m.session?(55)).to be_truthy
    end

    it 'should retrieve a document by session id ' do
      documento = MetaDocument.new(55)
      m.push(documento)

      expect(m.document(55)).to eq(documento)
    end

    it 'should get the list of documents as a array' do
      expect(m.documents.class).to be([].class)
    end

    it 'should retrieve the list of documents inserted' do
      document = MetaDocument.new(0)

      m.push(document)
      expect(m.documents[0]).to eq(document)
    end

    it 'should not allow adding more then one document in the same session' do
      m.push(MetaDocument.new(0))
      expect { m.push(MetaDocument.new(0)) }.to raise_error(ArgumentError)
    end

    it 'should allow adding more then one document in different sessions' do
      m.push(MetaDocument.new(1))
      expect { m.push(MetaDocument.new(2)) }.to_not raise_error
    end

    it 'should merge a list of pdfs of all sessions' do
      document1 = MetaDocument.new(1)
      document1.links.push('pdf1', 'pdf2')

      document2 = MetaDocument.new(2)
      document2.links.push('pdf3', 'pdf4')

      m.push(document1)
      m.push(document2)

      expect(m.merge_links).to eq(%w(pdf1 pdf2 pdf3 pdf4))
    end
  end
end
