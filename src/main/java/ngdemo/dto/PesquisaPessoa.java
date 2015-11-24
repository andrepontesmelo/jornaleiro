package ngdemo.dto;

import ngdemo.service.MySQLAccess;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.*;
import java.util.Vector;

@XmlRootElement
public class PesquisaPessoa {

    private Vector<Pessoa> pessoas;

    public PesquisaPessoa()
    {
        try {
            this.pessoas = getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Vector<Pessoa> getAll() throws SQLException {
        Vector<Pessoa> usuarios = new Vector<Pessoa>();

        Connection connection = MySQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        ResultSet result = s.executeQuery("select p.nome,p.codigo, l.nome from pessoa p " +
                " join endereco e on p.codigo = e.pessoa " +
                " join localidade l on e.localidade=l.codigo limit 500");

        while (result.next()) {
            Pessoa novo = new Pessoa(result.getString(1));
            novo.setCodigo(result.getInt(2));
            novo.setCidade(result.getString(3));
            usuarios.add(novo);
        }

        s.close();
        connection.close();

        return usuarios;
    }

    public Vector<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(Vector<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
}
