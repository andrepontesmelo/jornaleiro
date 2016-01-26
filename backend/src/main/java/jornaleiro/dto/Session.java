package jornaleiro.dto;

import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement
public class Session {
    private String title;
    private Journal journal;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Session() {
    }
}
