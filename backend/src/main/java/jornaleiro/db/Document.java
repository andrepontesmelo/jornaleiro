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

        ResultSet result = s.executeQuery(str);

        jornaleiro.dto.Document d = new jornaleiro.dto.Document();

        if (result.next()) {

            d.setId(id);
            d.setContent(result.getString(1));
            d.setSession(jornaleiro.db.Session.getSession(result.getInt(2)));
            d.setPage(result.getInt(3));
            d.setDate(result.getDate(4));
            d.setTitle(result.getString(5));
            d.setUrl(result.getString(6));
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
