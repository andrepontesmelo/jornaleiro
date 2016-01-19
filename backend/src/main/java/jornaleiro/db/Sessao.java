package jornaleiro.db;

import jornaleiro.dto.Jornal;
import jornaleiro.service.PgSQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Sessao {
    private static HashMap<Integer, jornaleiro.dto.Sessao> hashSessoes = null;

    public static jornaleiro.dto.Sessao obterSessao(int id) throws Exception
    {
        if (hashSessoes == null || hashSessoes.size() == 0)
        {
            hashSessoes = new HashMap<Integer, jornaleiro.dto.Sessao>();
            List<jornaleiro.dto.Sessao> sessoes = obter();

            for (jornaleiro.dto.Sessao s : sessoes) {
                hashSessoes.put(s.getId(), s);
            }
        }

        jornaleiro.dto.Sessao sessao = hashSessoes.get(id);

        if (sessao == null) throw new Exception("Nulo viu");

        return sessao;
    }


    private static List<jornaleiro.dto.Sessao> obter() throws Exception  {

        List<jornaleiro.dto.Sessao> sessoes = new LinkedList<jornaleiro.dto.Sessao>();

        Connection connection = PgSQLAccess.getInstance().getConnection();

        if (connection == null)
            throw new Exception("Conexão nula");

        Statement s = connection.createStatement();

        StringBuilder str = new StringBuilder();

        str.append("select id, titulo, jornal from sessao");

        ResultSet resultado = s.executeQuery(str.toString());


        while (resultado.next()) {

            jornaleiro.dto.Sessao sessao;
            sessao = new jornaleiro.dto.Sessao();

            sessao.setId(resultado.getInt(1));
            sessao.setTitulo(resultado.getString(2));
            sessao.setJornal(jornaleiro.db.Jornal.obter(resultado.getInt(3)));

            sessoes.add(sessao);
        }

        s.close();
        connection.close();

        return sessoes;
    }
}
