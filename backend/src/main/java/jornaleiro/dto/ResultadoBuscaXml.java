package jornaleiro.dto;

import jornaleiro.db.Busca;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Vector;

@XmlRootElement
public class ResultadoBuscaXml {

    private String query;
    private Vector<ResultadoBusca> resultadoBusca;

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
        this.resultadoBusca = Busca.buscar(query);
    }

    public void filtrarTexto(String query) {

        if (resultadoBusca == null)
            throw new NullPointerException("A busca não foi feita");

        for (ResultadoBusca r : resultadoBusca) {
            r.filtrarTexto(query);
        }
    }
}