package jornaleiro.service;

import jornaleiro.dto.SearchResultXml;
import jornaleiro.dto.DocumentXml;

public class Service  {
    public SearchResultXml getSearchResultXml(String query) throws Exception
    {
        SearchResultXml result = new SearchResultXml(query);

        result.filter(query);
        return result;
    }

    public DocumentXml getDocumentXml(int id) throws Exception {
        return new DocumentXml(id);
    }
}
