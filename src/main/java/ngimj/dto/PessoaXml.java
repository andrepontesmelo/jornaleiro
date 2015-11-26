package ngimj.dto;

import ngimj.service.MySQLAccess;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.*;
import java.util.Vector;

@XmlRootElement
public class PessoaXml {

    private Vector<Pessoa> pessoas;

    public PessoaXml()
    {
        try {
            this.pessoas = ngimj.db.Pessoa.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(Vector<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
}
