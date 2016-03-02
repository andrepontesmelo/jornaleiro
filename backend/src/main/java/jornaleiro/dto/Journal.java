package jornaleiro.dto;

import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement
public class Journal {
    int id;
    String name;

    public Journal() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
