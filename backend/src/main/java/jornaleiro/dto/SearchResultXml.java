package jornaleiro.dto;

import jornaleiro.db.Search;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Vector;

@XmlRootElement
public class SearchResultXml {

    private String query;
    private Vector<SearchResult> searchResult;

    public double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    private double elapsedTime;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Vector<SearchResult> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(Vector<SearchResult> searchResult) {
        this.searchResult = searchResult;
    }

    public SearchResultXml(String query) throws Exception
    {
        this.query = query;

        long start = System.nanoTime();
        this.searchResult = Search.search(query);

        elapsedTime = (double) (System.nanoTime() - start) / 1000000000.0;
    }

    public void filter(String query) {

        if (searchResult == null)
            throw new NullPointerException("Result is null.");

        for (SearchResult r : searchResult) {
            r.filter(query);
        }
    }
}