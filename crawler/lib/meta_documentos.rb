module Jornaleiro

  class MetaDocumento
    attr_accessor :sessao_id, :sessao, :paginas, :lista_pdfs

    def initialize(sessao_id)
      @sessao_id = sessao_id
      @lista_pdfs = []
    end
  end

  class MetaDocumentos
    attr_accessor :documentos

    def initialize
      @documentos = {}
    end

    def adiciona(meta_documento)

      chave = meta_documento.sessao_id

      raise ArgumentError if (@documentos.has_key? chave)

      @documentos[chave] = meta_documento
    end

    def contem_sessao(sessao_id)
      @documentos.has_key? sessao_id
    end

    def obtem_documento(sessao_id)
      @documentos[sessao_id]
    end

    def obtem_lista
      @documentos.values
    end

    def obtem_lista_pdfs

      uniao = []
      puts "Quantidade de documentos: #{@documentos.values.count}"

      @documentos.values.each { |d|
        uniao += (d.lista_pdfs)
      }

      uniao
    end
  end
end