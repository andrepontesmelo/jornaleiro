package ngdemo.dto;

import ngdemo.service.MySQLAccess;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

@XmlRootElement
public class MercadoriaResource {

    final static Logger logger = Logger.getLogger(MercadoriaResource.class);

    private Vector<Mercadoria> mercadorias;

    public MercadoriaResource()
    {
        try {
            this.mercadorias = getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Vector<Mercadoria> getAll() throws SQLException {
        Vector<Mercadoria> mercadorias = new Vector<Mercadoria>();


        Connection connection = MySQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        ResultSet result = s.executeQuery("select referencia, faixa from mercadoria ");

        while (result.next()) {
            Mercadoria novo = new Mercadoria();
            novo.setReferencia(result.getString(1));
            String bla = result.getString(2);

            novo.setFaixa(bla);

            try {
            } catch (Exception err)
            {
                logger.error(err.toString());
            }

            mercadorias.add(novo);
        }

        s.close();
        connection.close();

        return mercadorias;
    }

    public Vector<Mercadoria> getMercadorias() {
        return mercadorias;
    }

    public void setMercadorias(Vector<Mercadoria> mercadorias) {
        this.mercadorias = mercadorias;
    }
}

