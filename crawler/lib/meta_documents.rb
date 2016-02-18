module Jornaleiro
  # information about a session of documents to be retrieved
  class MetaDocument
    attr_accessor :session_id, :session, :page_count, :links

    def initialize(session_id)
      @session_id = session_id
      @links = []
    end
  end

  # Dictionary of meta documents.
  class MetaDocuments
    attr_accessor :docs

    def initialize
      @docs = {}
    end

    def push(meta_document)
      key = meta_document.session_id
      raise ArgumentError if @docs.key? key
      @docs[key] = meta_document
    end

    def session?(sessao_id)
      @docs.key? sessao_id
    end

    def document(sessao_id)
      @docs[sessao_id]
    end

    def documents
      @docs.values
    end

    def merge_links
      merge = []

      @docs.values.each do |d|
        merge += d.links
      end

      merge
    end
  end
end
