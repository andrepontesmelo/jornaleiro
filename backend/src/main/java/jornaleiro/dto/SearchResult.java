package jornaleiro.dto;

import com.sun.jersey.api.NotFoundException;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@XmlRootElement
public class SearchResult {

    private int id;
    private int page;
    private String content;
    private Session session;
    private Date date;
    private long age;
    private String title;
    private String url;

    public SearchResult() {
    }

    public int getId() {
        return id;
    }

    public long getAge() { return age; }

    public void setAge(long age) { this.age = age; }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {

        this.date = date;
        this.age = TimeUnit.MILLISECONDS.toDays(new Date().getTime() - date.getTime());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void filter(String query) {
        int position = content.toLowerCase().indexOf(query.toLowerCase());

        if (position >= 0)
        {
            int begin = Math.max(0, position - 100);
            int end = Math.min(content.length(), position + 100);

            content = content.substring(begin, end);
        } else
            throw new NotFoundException("query term was not found in the document");
    }
}
