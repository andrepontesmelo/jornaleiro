package jornaleiro.db;

import jornaleiro.service.PgSQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Jornal {
    private static HashMap<Integer, jornaleiro.dto.Jornal> hashJornal = null;

    public static jornaleiro.dto.Jornal obter(int id) throws Exception
    {
        if (hashJornal == null)
        {
            hashJornal = new HashMap<Integer, jornaleiro.dto.Jornal>();
            List<jornaleiro.dto.Jornal> jornais = obter();

            for (jornaleiro.dto.Jornal j : jornais) {
                hashJornal.put(j.getId(), j);
            }
        }

        return hashJornal.get(id);
    }

    private static List<jornaleiro.dto.Jornal> obter() throws Exception  {

        List<jornaleiro.dto.Jornal> jornais = new LinkedList<jornaleiro.dto.Jornal>();

        Connection connection = PgSQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        StringBuilder str = new StringBuilder();

        str.append("select id, nome from jornal");

        ResultSet resultado = s.executeQuery(str.toString());

        while (resultado.next()) {

            jornaleiro.dto.Jornal jornal;
            jornal = new jornaleiro.dto.Jornal();

            jornal.setId(resultado.getInt(1));
            jornal.setNome(resultado.getString(2));

            jornais.add(jornal);
        }

        s.close();
        connection.close();

        return jornais;
    }
}
