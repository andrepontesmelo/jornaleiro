package jornaleiro.dto;

import jornaleiro.db.Busca;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Vector;

@XmlRootElement
public class ResultadoBuscaXml {

    private String query;
    private Vector<ResultadoBusca> resultadoBusca;

    public double getTempoDecorrido() {
        return tempoDecorrido;
    }

    public void setTempoDecorrido(double tempoDecorrido) {
        this.tempoDecorrido = tempoDecorrido;
    }

    private double tempoDecorrido;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Vector<ResultadoBusca> getResultadoBusca() {
        return resultadoBusca;
    }

    public void setResultadoBusca(Vector<ResultadoBusca> resultadoBusca) {
        this.resultadoBusca = resultadoBusca;
    }

    public ResultadoBuscaXml(String query) throws Exception
    {
        this.query = query;

        long inicio = System.nanoTime();
        this.resultadoBusca = Busca.buscar(query);

        tempoDecorrido = (double) (System.nanoTime() - inicio) / 1000000000.0;
    }

    public void filtrarTexto(String query) {

        if (resultadoBusca == null)
            throw new NullPointerException("A busca não foi feita");

        for (ResultadoBusca r : resultadoBusca) {
            r.filtrarTexto(query);
        }
    }
}