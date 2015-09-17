package ngdemo.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.*;
import java.util.Vector;

@XmlRootElement
// from http://www.vogella.com/articles/REST/
// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class User {

    private Vector<Pessoa> users;

    public User()
    {
        try {
            this.users = getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Vector<Pessoa> getAll() throws SQLException {
        Vector<Pessoa> usuarios = new Vector<Pessoa>();

        //String url = "jdbc:mysql://localhost:3306/imjoias";
        String url = "jdbc:mysql://192.168.1.25:46033/imjoias";
        String username = "root";
        String password = "mircvinhad";

        System.out.println("Connecting database...");

        //usuarios.add(new Pessoa("iniico"));

        Connection connection = null;
        try {
            try {
                //usuarios.add(new Pessoa("ler jdbc"));

                Class.forName("com.mysql.jdbc.Driver"); // essa linha pode resolver o problema
                //usuarios.add(new Pessoa("jdbc ok"));

            } catch (ClassNotFoundException e) {
                return usuarios;
            }
            connection = DriverManager.getConnection(url, username, password);
           // usuarios.add(new Pessoa("get connection ok"));

            if (connection == null)
            {
                return usuarios;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return usuarios;

        }
        //usuarios.add(new Pessoa("criar conn"));

        Statement s = connection.createStatement();

        ResultSet result = s.executeQuery("select p.nome,p.codigo, l.nome from pessoa p join endereco e on p.codigo=e.pessoa join localidade l on e.localidade=l.codigo");
        //usuarios.add(new Pessoa("vai executar"));

        while (result.next()) {
            Pessoa novo = new Pessoa(result.getString(1));
            novo.setCodigo(result.getInt(2));
            //novo.atribuiCodigo("55");
            novo.setCidade(result.getString(3));
            usuarios.add(novo);
        }
        //usuarios.add(new Pessoa("executou"));

        s.close();
        connection.close();

        System.out.println("Database connected!");

        return usuarios;
    }


    public Vector<Pessoa> getUsers() {
        return users;
    }

    public void setUsers(Vector<Pessoa> users) {
        this.users = users;
    }
}
