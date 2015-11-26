package ngimj.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mercadoria {
    private String referencia;
    private String faixa;

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }


    public String getFaixa() {
        return faixa;
    }

    public void setFaixa(String faixa) {
        this.faixa = faixa;
    }

    public Mercadoria()
    {
    }
}
