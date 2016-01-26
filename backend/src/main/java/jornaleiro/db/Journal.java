package jornaleiro.db;

import jornaleiro.service.PgSQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Journal {
    private static HashMap<Integer, jornaleiro.dto.Journal> hashJournal = null;

    public static jornaleiro.dto.Journal getAll(int id) throws Exception
    {
        if (hashJournal == null)
        {
            hashJournal = new HashMap<Integer, jornaleiro.dto.Journal>();
            List<jornaleiro.dto.Journal> journals = getAll();

            for (jornaleiro.dto.Journal j : journals) {
                hashJournal.put(j.getId(), j);
            }
        }

        return hashJournal.get(id);
    }

    private static List<jornaleiro.dto.Journal> getAll() throws Exception  {

        List<jornaleiro.dto.Journal> jornais = new LinkedList<jornaleiro.dto.Journal>();

        Connection connection = PgSQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        ResultSet resultSet = s.executeQuery("select id, name from journal");

        while (resultSet.next()) {

            jornaleiro.dto.Journal journal;
            journal = new jornaleiro.dto.Journal();

            journal.setId(resultSet.getInt(1));
            journal.setName(resultSet.getString(2));

            jornais.add(journal);
        }

        s.close();
        connection.close();

        return jornais;
    }
}
