package com.jornaleirooficial.jornaleiro.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.jornaleirooficial.jornaleiro.provider.query.QueryColumns;
import com.jornaleirooficial.jornaleiro.provider.query.QueryContentValues;
import com.jornaleirooficial.jornaleiro.provider.query.QueryCursor;
import com.jornaleirooficial.jornaleiro.provider.query.QuerySelection;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private String phrase;
    private ContentResolver resolver;

    public Query(QueryCursor cursor, ContentResolver resolver) {
        this(resolver);
        this.phrase = cursor.getPhrase();
    }

    public Query(ContentResolver resolver) {
        this.resolver = resolver;
    }

    public static List<Query> getAll(ContentResolver resolver) {
        List<Query> queries = new ArrayList<>();

        QuerySelection where = new QuerySelection();

        Cursor c = resolver.query(QueryColumns.CONTENT_URI, null, where.sel(), where.args(), null);

        while (c != null && c.getCount() > 0 && c.moveToNext()) {
            queries.add(new Query(new QueryCursor(c), resolver));
        }

        if (c != null)
            c.close();

        return queries;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public void Insert() {
        QueryContentValues values = new QueryContentValues();
        values.putPhrase(phrase);
        Uri uri = values.insert(resolver);
    }
}
