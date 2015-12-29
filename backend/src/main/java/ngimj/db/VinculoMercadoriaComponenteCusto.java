package ngimj.db;

import ngimj.dto.*;
import ngimj.service.MySQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class VinculoMercadoriaComponenteCusto {
    public static Vector<ngimj.dto.VinculoMercadoriaComponenteCusto> get(String referencia) throws SQLException {
        Vector<ngimj.dto.VinculoMercadoriaComponenteCusto> componentes = new Vector<ngimj.dto.VinculoMercadoriaComponenteCusto>();

        Connection connection = MySQLAccess.getInstance().getConnection();

        if (connection == null)
            return new Vector<ngimj.dto.VinculoMercadoriaComponenteCusto>();

        Statement s = connection.createStatement();

        ResultSet result = s.executeQuery("select codigo, nome, quantidade from componentecusto c join  " +
                " vinculomercadoriacomponentecusto v " +
                " ON c.codigo=v.componentecusto WHERE v.mercadoria='" + referencia + "'");

        while (result.next()) {
            ngimj.dto.VinculoMercadoriaComponenteCusto novo = new ngimj.dto.VinculoMercadoriaComponenteCusto();
            novo.setCodigo(result.getString(1));
            novo.setNome(result.getString(2));
            novo.setQuantidade(result.getDouble(3));
            componentes.add(novo);
        }


        s.close();
        connection.close();

        return componentes;
    }

}
