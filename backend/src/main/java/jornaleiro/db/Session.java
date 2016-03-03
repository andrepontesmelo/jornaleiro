package jornaleiro.db;

import jornaleiro.service.PgSQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Session {
    private static HashMap<Integer, jornaleiro.dto.Session> hashSession = null;
    private static ArrayList<jornaleiro.dto.Session> sessions = null;

    public static jornaleiro.dto.Session getSession(int id) throws Exception {
        if (hashSession == null || hashSession.size() == 0)
            createCache();

        jornaleiro.dto.Session session = hashSession.get(id);

        if (session == null)
            throw new NullPointerException();

        return session;
    }

    private static void createCache() throws Exception {
        hashSession = new HashMap<>();
        sessions = get();

        for (jornaleiro.dto.Session s : sessions) {
            hashSession.put(s.getId(), s);
        }
    }

    public static ArrayList<jornaleiro.dto.Session> getAll() throws Exception {
        if (sessions == null)
            createCache();

        return sessions;
    }

    private static ArrayList<jornaleiro.dto.Session> get() throws Exception {

        ArrayList<jornaleiro.dto.Session> sessionList = new ArrayList<>();

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
