package jornaleiro.db;

import jornaleiro.dto.Session;
import jornaleiro.service.PgSQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Document {
    public static jornaleiro.dto.Document get(int id) throws Exception  {

        Connection connection = PgSQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        String str = "select content, session, page, date, title, url from document where id = " + id;

        ResultSet resultado = s.executeQuery(str);

        jornaleiro.dto.Document d = new jornaleiro.dto.Document();

        if (resultado.next()) {

            d.setId(id);
            d.setContent(resultado.getString(1));
            d.setSession(jornaleiro.db.Session.getSession(resultado.getInt(2)));
            d.setPage(resultado.getInt(3));
            d.setDate(resultado.getDate(4));
            d.setTitle(resultado.getString(5));
            d.setUrl(resultado.getString(6));
        }

        s.close();
        connection.close();

        return d;
    }

    public static Integer getId(Date date, Session session, int page) throws SQLException {

        if (page < 0)
            return null;

        Connection connection = PgSQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        String str = "select id from document where date='" +
                date.toString() +
                "' and page=" +
                page +
                " and session=" +
                session.getId();


        ResultSet result = s.executeQuery(str);

        Integer read = null;

        if (result.next()) {
          read = result.getInt(1);
        }

        s.close();
        connection.close();

        return read;
    }
}
