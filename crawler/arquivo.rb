class Arquivo
  def le(sessao, data, pagina)
    conteudo = File.read("/tmp/#{sessao}_#{data} #{pagina}.pdf?sequence=1.transcrito")

    conteudo
  end
end
