package ngimj.db;

import ngimj.dto.MercadoriaXml;
import ngimj.service.MySQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Mercadoria {
    public static Vector<ngimj.dto.Mercadoria> getAll() throws SQLException {
        Vector<ngimj.dto.Mercadoria> mercadorias = new Vector<ngimj.dto.Mercadoria>();


        Connection connection = MySQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        ResultSet result = s.executeQuery("select referencia, faixa from mercadoria ");

        while (result.next()) {
            ngimj.dto.Mercadoria novo = new ngimj.dto.Mercadoria();
            novo.setReferencia(result.getString(1));
            novo.setFaixa(result.getString(2));
            mercadorias.add(novo);
        }

        s.close();
        connection.close();

        return mercadorias;
    }

    public static ngimj.dto.Mercadoria get(String referencia) throws SQLException {

        ngimj.dto.Mercadoria novo = null;
        Connection connection = MySQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        ResultSet result = s.executeQuery("select referencia, faixa, teor, nome, peso, foradelinha " +
                " from mercadoria where referencia='" + referencia + "'");

        while (result.next()) {
            novo = new ngimj.dto.Mercadoria();
            novo.setReferencia(result.getString(1));
            novo.setFaixa(result.getString(2));
            novo.setTeor(result.getInt(3));
            novo.setNome(result.getString(4));
            novo.setPeso(result.getDouble(5));
            novo.setForaDeLinha(result.getBoolean(6));
        }

        s.close();
        connection.close();

        return novo;
    }

    public static void update(MercadoriaXml mercadoria) throws Exception {

        Connection connection = MySQLAccess.getInstance().getConnection();

        if (connection == null)
            return;

        connection.setAutoCommit(true);
        Statement s = connection.createStatement();

        ngimj.dto.Mercadoria m = mercadoria.getMercadoria();

        s.execute("UPDATE mercadoria set faixa='" + m.getFaixa() + "', " +
                " teor=" + m.getTeor() + ", " +
                " nome='" + m.getNome() + "', " +
                " peso=" + m.getPeso() + ", " +
                " foradelinha=" + m.isForaDeLinha() +
                " WHERE referencia='" + m.getReferencia() + "' ");


        //connection.commit();
    }
}
