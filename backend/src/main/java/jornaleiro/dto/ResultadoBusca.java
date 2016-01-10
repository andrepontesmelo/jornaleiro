package jornaleiro.dto;

import com.sun.jersey.api.NotFoundException;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class ResultadoBusca {

    private int idDocumento;
    private int pagina;
    private String texto;
    private int sessao;
    private Date data;
    private String titulo;
    private String url;

    public ResultadoBusca() {
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getSessao() {
        return sessao;
    }

    public void setSessao(int sessao) {
        this.sessao = sessao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void filtrarTexto(String query) {
        int posicao = texto.toLowerCase().indexOf(query.toLowerCase());

        if (posicao >= 0)
        {
            int inicio = Math.max(0, posicao - 50);
            int maximo = Math.min(texto.length(), posicao + 50);

            texto = texto.substring(inicio, maximo);
        } else
            throw new NotFoundException("termo não encontrado no documento");
    }
}
