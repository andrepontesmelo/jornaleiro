package jornaleiro.db;

import jornaleiro.dto.*;
import jornaleiro.service.MySQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Busca {
    public static Vector<ResultadoBusca> buscar(String query) throws SQLException {
        Vector<ResultadoBusca> resultadoBusca = new Vector<ResultadoBusca>();

        Connection connection = MySQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        ResultSet resultado =
                s.executeQuery("select id,texto,sessao,pagina,data,titulo,url from documento where match(texto) " +
                        " against('+\"" + query + "\"' in boolean mode) limit 100");

        while (resultado.next()) {
            ResultadoBusca novo = new ResultadoBusca();

            novo.setIdDocumento(resultado.getInt(1));
            novo.setTexto(resultado.getString(2));
            novo.setSessao(resultado.getInt(3));
            novo.setPagina(resultado.getInt(4));
            novo.setData(resultado.getDate(5));
            novo.setTitulo(resultado.getString(6));
            novo.setUrl(resultado.getString(7));

            novo.setUrl(query);

            resultadoBusca.add(novo);
        }

        s.close();
        connection.close();

        return resultadoBusca;
    }
}
