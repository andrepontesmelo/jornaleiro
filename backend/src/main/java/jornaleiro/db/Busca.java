package jornaleiro.db;

import jornaleiro.dto.*;
import jornaleiro.service.PgSQLAccess;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Busca {
    public static Vector<ResultadoBusca> buscar(String query) throws Exception  {
        Vector<ResultadoBusca> resultadoBusca = new Vector<ResultadoBusca>();

        query = query.toLowerCase().trim();

        Connection connection = PgSQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        StringBuilder str = new StringBuilder();

        str.append("select id,textoMinusculo,sessao,pagina,data,titulo,url from documento where ");
        str.append(" to_tsvector('portuguese'::regconfig, textoMinusculo) @@ to_tsquery('");

        String[] partes = query.split(" ");

        boolean primeiro = true;
        for (String parte : partes) {

            if (!primeiro)
                str.append(" & ");

            str.append(parte);

            primeiro = false;
        }

        str.append("') AND textoMinusculo like '%");
        str.append(query);
        str.append("%' order by data desc LIMIT 40 ");

        ResultSet resultado = s.executeQuery(str.toString());

        while (resultado.next()) {
            ResultadoBusca novo = new ResultadoBusca();

            novo.setIdDocumento(resultado.getInt(1));
            novo.setTexto(resultado.getString(2));
            novo.setSessao(Sessao.obterSessao(resultado.getInt(3)));
            novo.setPagina(resultado.getInt(4));
            novo.setData(resultado.getDate(5));
            novo.setTitulo(resultado.getString(6));
            novo.setUrl(resultado.getString(7));

            resultadoBusca.add(novo);
        }

        s.close();
        connection.close();

        return resultadoBusca;
    }
}