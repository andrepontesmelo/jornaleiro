module Jornaleiro

  class MetaDocument
    attr_accessor :session_id, :session, :page_count, :links

    def initialize(session_id)
      @session_id = session_id
      @links = []
    end
  end

  class MetaDocuments
    attr_accessor :docs

    def initialize
      @docs = {}
    end

    def push(meta_document)

      key = meta_document.session_id

      raise ArgumentError if (@docs.has_key? key)

      @docs[key] = meta_document
    end

    def has_session?(sessao_id)
      @docs.has_key? sessao_id
    end

    def get_document(sessao_id)
      @docs[sessao_id]
    end

    def get_documents
      @docs.values
    end

    def merge_links

      merge = []

      @docs.values.each { |d|
        merge += (d.links)
      }

      merge
    end
  end
end