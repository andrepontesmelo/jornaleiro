package jornaleiro.db;

import jornaleiro.dto.*;
import jornaleiro.service.PgSQLAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Search {
    public static Vector<SearchResult> search(String query) throws Exception  {
        Vector<SearchResult> searchResult = new Vector<SearchResult>();

        query = query.toLowerCase().trim();

        Connection connection = PgSQLAccess.getInstance().getConnection();

        if (connection == null)
            return null;

        Statement s = connection.createStatement();

        StringBuilder str = new StringBuilder();

        str.append("select id, content, session, page, date, title, url from document where ");
        str.append(" to_tsvector('portuguese'::regconfig, content) @@ to_tsquery('");

        String[] slices = query.split(" ");

        boolean first = true;
        for (String part : slices) {

            if (!first)
                str.append(" & ");

            str.append(part);

            first = false;
        }

        str.append("') AND content like '%");
        str.append(query);
        str.append("%' order by date desc LIMIT 40 ");

        ResultSet result = s.executeQuery(str.toString());

        while (result.next()) {
            SearchResult newResult = new SearchResult();

            newResult.setId(result.getInt(1));
            newResult.setContent(result.getString(2));
            newResult.setSession(Session.getSession(result.getInt(3)));
            newResult.setPage(result.getInt(4));
            newResult.setDate(result.getDate(5));
            newResult.setTitle(result.getString(6));
            newResult.setUrl(result.getString(7));

            searchResult.add(newResult);
        }

        s.close();
        connection.close();

        return searchResult;
    }
}