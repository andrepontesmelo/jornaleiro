package jornaleiro.dto;

import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement
public class Jornal {
    int id;
    String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Jornal() {
        this.nome = "Diário oficial do estado de Minas Gerais";
        this.id = 55;
    }
}
