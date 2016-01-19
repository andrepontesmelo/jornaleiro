package jornaleiro.service;

import jornaleiro.dto.ResultadoBuscaXml;
import jornaleiro.dto.ResultadoDocumentoXml;

public class Service  {
    public ResultadoBuscaXml obtemResultadoBuscaXml(String query) throws Exception
    {
        ResultadoBuscaXml resultado = new ResultadoBuscaXml(query);

        resultado.filtrarTexto(query);
        return resultado;
    }

    public ResultadoDocumentoXml obtemDocumentoXml(int id) throws Exception {
        return new ResultadoDocumentoXml(id);
    }


}
