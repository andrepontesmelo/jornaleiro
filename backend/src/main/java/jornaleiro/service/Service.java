package jornaleiro.service;

import jornaleiro.dto.ResultadoBuscaXml;

public class Service  {
    public ResultadoBuscaXml obtemResultadoBuscaXml(String query) throws Exception
    {
        ResultadoBuscaXml resultado = new ResultadoBuscaXml(query);

        resultado.filtrarTexto(query);
        return resultado;
    }
}
