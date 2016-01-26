package jornaleiro.dto;

import junit.framework.TestCase;


public class SearchResultTest extends TestCase {

    public void testFilter() throws Exception {

        // it should filter exact term within content
        SearchResult result = new SearchResult();
        result.setContent("exact term");
        result.filter("exact term");

        assertEquals("exact term", result.getContent());
    }

}