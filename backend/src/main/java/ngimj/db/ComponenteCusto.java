package ngimj.db;

import ngimj.service.MySQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class ComponenteCusto {


    public static Vector<ngimj.dto.ComponenteCusto> getAll() throws SQLException {
        Vector<ngimj.dto.ComponenteCusto> componentes = new Vector<ngimj.dto.ComponenteCusto>();

        Connection connection = MySQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        ResultSet result = s.executeQuery("select codigo, nome from componentecusto ");

        while (result.next()) {
            ngimj.dto.ComponenteCusto novo = new ngimj.dto.ComponenteCusto();
            novo.setCodigo(result.getString(1));
            novo.setNome(result.getString(2));
            componentes.add(novo);
        }

        s.close();
        connection.close();

        return componentes;
    }
}
