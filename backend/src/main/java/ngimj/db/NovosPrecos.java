package ngimj.db;

import ngimj.service.MySQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NovosPrecos {

    public static ngimj.dto.NovosPrecos get(String referencia) throws SQLException {
        Connection connection = MySQLAccess.getInstance().getConnection();

        Statement s = connection.createStatement();
        s.execute("call lerParametrosGeracaoPrecos;");

        s = connection.createStatement();
        ResultSet result = s.executeQuery("select novoIndiceAtacado, novoPrecoCusto from novosPrecos " +
                " where mercadoria='" + referencia + "'");

        ngimj.dto.NovosPrecos novo = null;

        if (result.next()) {
            novo = new ngimj.dto.NovosPrecos();

            novo.setNovoIndiceAtacado(result.getDouble(1));
            novo.setNovoPrecoCusto(result.getDouble(2));
        }

        return novo;
    }
}
