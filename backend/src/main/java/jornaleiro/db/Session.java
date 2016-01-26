package jornaleiro.db;

import jornaleiro.service.PgSQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Session {
    private static HashMap<Integer, jornaleiro.dto.Session> hashSession = null;

    public static jornaleiro.dto.Session getSession(int id) throws Exception
    {
        if (hashSession == null || hashSession.size() == 0)
        {
            hashSession = new HashMap<Integer, jornaleiro.dto.Session>();
            List<jornaleiro.dto.Session> sessionList = get();

            for (jornaleiro.dto.Session s : sessionList) {
                hashSession.put(s.getId(), s);
            }
        }
        jornaleiro.dto.Session session = hashSession.get(id);

        if (session == null)
            throw new NullPointerException();

        return session;
    }


    private static List<jornaleiro.dto.Session> get() throws Exception  {

        List<jornaleiro.dto.Session> sessionList = new LinkedList<jornaleiro.dto.Session>();

        Connection connection = PgSQLAccess.getInstance().getConnection();

        if (connection == null)
            throw new NullPointerException("Connection is null.");

        Statement s = connection.createStatement();

        ResultSet result = s.executeQuery("select id, title, journal from session");

        while (result.next()) {

            jornaleiro.dto.Session session;
            session = new jornaleiro.dto.Session();

            session.setId(result.getInt(1));
            session.setTitle(result.getString(2));
            session.setJournal(Journal.getAll(result.getInt(3)));

            sessionList.add(session);
        }

        s.close();
        connection.close();

        return sessionList;
    }
}
