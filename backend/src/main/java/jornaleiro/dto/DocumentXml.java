package jornaleiro.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentXml {

    private int id;
    private Document document;
    private Integer before;
    private Integer after;

    public Integer getAfter() {
        return after;
    }

    public void setAfter(Integer after) {
        this.after = after;
    }

    public Integer getBefore() {
        return before;
    }

    public void setBefore(Integer before) {
        this.before = before;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DocumentXml(int id) throws Exception
    {
        this.id = id;
        this.document = jornaleiro.db.Document.get(id);

        if (document != null) {
            setAfter(jornaleiro.db.Document.getId(document.getDate(), document.getSession(), document.getPage() + 1));
            setBefore(jornaleiro.db.Document.getId(document.getDate(), document.getSession(), document.getPage() - 1));
        }
    }
}