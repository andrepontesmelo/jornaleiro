package jornaleiro.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultadoDocumentoXml {

    private int id;
    private Documento documento;
    private Integer anterior;
    private Integer posterior;

    public Integer getPosterior() {
        return posterior;
    }

    public void setPosterior(Integer posterior) {
        this.posterior = posterior;
    }

    public Integer getAnterior() {
        return anterior;
    }

    public void setAnterior(Integer anterior) {
        this.anterior = anterior;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResultadoDocumentoXml(int id) throws Exception
    {
        this.id = id;
        this.documento = jornaleiro.db.Documento.obter(id);

        if (documento != null) {
            setPosterior(jornaleiro.db.Documento.obterId(documento.getData(), documento.getSessao(), documento.getPagina() + 1));
            setAnterior(jornaleiro.db.Documento.obterId(documento.getData(), documento.getSessao(), documento.getPagina() - 1));
        }
    }
}