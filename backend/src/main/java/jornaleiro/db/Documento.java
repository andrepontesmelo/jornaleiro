package jornaleiro.db;

import jornaleiro.dto.ResultadoBusca;
import jornaleiro.dto.Sessao;
import jornaleiro.service.PgSQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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
            d.setSessao(jornaleiro.db.Sessao.obterSessao(resultado.getInt(2)));
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

    public static Integer obterId(Date data, Sessao sessao, int pagina) throws SQLException {

        if (pagina < 0)
            return null;

        Connection connection = PgSQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        StringBuilder str = new StringBuilder();

        str.append("select id from documento where data='");
        str.append(data.toString());
        str.append("' and pagina=");
        str.append(pagina);
        str.append(" and sessao=");
        str.append(sessao.getId());


        ResultSet resultado = s.executeQuery(str.toString());

        Integer lido = null;

        if (resultado.next()) {
          lido = resultado.getInt(1);
        }

        s.close();
        connection.close();

        return lido;
    }

}
