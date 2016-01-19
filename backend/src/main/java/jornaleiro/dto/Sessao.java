package jornaleiro.dto;

import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement
public class Sessao {
    private String titulo;
    private Jornal jornal;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Jornal getJornal() {
        return jornal;
    }

    public void setJornal(Jornal jornal) {
        this.jornal = jornal;
    }

    public Sessao() {
    }
}
