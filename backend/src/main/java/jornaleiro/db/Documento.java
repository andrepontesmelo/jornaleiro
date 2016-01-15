package jornaleiro.db;

import jornaleiro.dto.ResultadoBusca;
import jornaleiro.dto.Sessao;
import jornaleiro.service.PgSQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Documento {
    public static jornaleiro.dto.Documento obter(int id) throws Exception  {

        Connection connection = PgSQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        StringBuilder str = new StringBuilder();

        str.append("select textoMinusculo,sessao,pagina,data,titulo,url from documento where ");
        str.append(" id = ");
        str.append(id);

        ResultSet resultado = s.executeQuery(str.toString());

        jornaleiro.dto.Documento d;
        if (resultado.next()) {

            d = new jornaleiro.dto.Documento();
            d.setId(id);
            d.setTexto(resultado.getString(1));
            d.setSessao(new Sessao());
            d.setPagina(resultado.getInt(3));
            d.setData(resultado.getDate(4));
            d.setTitulo(resultado.getString(5));
            d.setUrl(resultado.getString(6));

        } else
        {
            d = new jornaleiro.dto.Documento();
            d.setUrl("Nada foi lido do banco de dados para o ID " + id);
        }

        s.close();
        connection.close();

        return d;
    }
}
