package jornaleiro.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultadoDocumentoXml {

    private int id;
    private Documento documento;
    private int anterior;
    private int posterior;

    public int getPosterior() {
        return posterior;
    }

    public void setPosterior(int posterior) {
        this.posterior = posterior;
    }

    public int getAnterior() {
        return anterior;
    }

    public void setAnterior(int anterior) {
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
        this.posterior = id + 1;
        this.anterior = id - 1;
    }
}