package ngimj.db;

import ngimj.service.MySQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Pessoa {

    public static Vector<ngimj.dto.Pessoa> getAll() throws SQLException {
        Vector<ngimj.dto.Pessoa> usuarios = new Vector<ngimj.dto.Pessoa>();

        Connection connection = MySQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        ResultSet result = s.executeQuery("select p.nome, p.codigo, min(l.nome) from pessoa p " +
                " join endereco e on p.codigo = e.pessoa " +
                " join localidade l on e.localidade=l.codigo GROUP BY p.codigo");

        while (result.next()) {
            ngimj.dto.Pessoa novo = new ngimj.dto.Pessoa(result.getString(1));
            novo.setCodigo(result.getInt(2));
            novo.setCidade(result.getString(3));
            usuarios.add(novo);
        }

        s.close();
        connection.close();

        return usuarios;
    }
}

