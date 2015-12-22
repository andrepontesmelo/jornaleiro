package ngimj.dto;

public class NovosPrecos {
    private double novoIndiceAtacado;
    private double novoPrecoCusto;

    private String mercadoria;

    public String getMercadoria() {
        return mercadoria;
    }

    public void setMercadoria(String mercadoria) {
        this.mercadoria = mercadoria;
    }

    public double getNovoIndiceAtacado() {
        return novoIndiceAtacado;
    }

    public void setNovoIndiceAtacado(double novoIndiceAtacado) {
        this.novoIndiceAtacado = novoIndiceAtacado;
    }

    public double getNovoPrecoCusto() {
        return novoPrecoCusto;
    }

    public void setNovoPrecoCusto(double novoPrecoCusto) {
        this.novoPrecoCusto = novoPrecoCusto;
    }
}
