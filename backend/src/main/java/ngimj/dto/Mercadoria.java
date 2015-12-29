package ngimj.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mercadoria {
    private String referencia;
    private String faixa;

    public String getNome() {
        return nome;
    }

    public void setNome(String descricao) {
        this.nome = descricao;
    }

    public int getTeor() {
        return teor;
    }

    public void setTeor(int teor) {
        this.teor = teor;
    }

    private String nome;
    private int teor;
    private double peso;

    public boolean isForaDeLinha() {
        return foraDeLinha;
    }

    public void setForaDeLinha(boolean foraDeLinha) {
        this.foraDeLinha = foraDeLinha;
    }

    private boolean foraDeLinha;

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

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
