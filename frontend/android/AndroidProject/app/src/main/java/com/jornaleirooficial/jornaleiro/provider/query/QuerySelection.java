package com.jornaleirooficial.jornaleiro.provider.query;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractSelection;

/**
 * Selection for the {@code query} table.
 */
public class QuerySelection extends AbstractSelection<QuerySelection> {
    @Override
    protected Uri baseUri() {
        return QueryColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code QueryCursor} object, which is positioned before the first entry, or null.
     */
    public QueryCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new QueryCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public QueryCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code QueryCursor} object, which is positioned before the first entry, or null.
     */
    public QueryCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new QueryCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public QueryCursor query(Context context) {
        return query(context, null);
    }


    public QuerySelection id(long... value) {
        addEquals("query." + QueryColumns._ID, toObjectArray(value));
        return this;
    }

    public QuerySelection idNot(long... value) {
        addNotEquals("query." + QueryColumns._ID, toObjectArray(value));
        return this;
    }

    public QuerySelection orderById(boolean desc) {
        orderBy("query." + QueryColumns._ID, desc);
        return this;
    }

    public QuerySelection orderById() {
        return orderById(false);
    }

    public QuerySelection phrase(String... value) {
        addEquals(QueryColumns.PHRASE, value);
        return this;
    }

    public QuerySelection phraseNot(String... value) {
        addNotEquals(QueryColumns.PHRASE, value);
        return this;
    }

    public QuerySelection phraseLike(String... value) {
        addLike(QueryColumns.PHRASE, value);
        return this;
    }

    public QuerySelection phraseContains(String... value) {
        addContains(QueryColumns.PHRASE, value);
        return this;
    }

    public QuerySelection phraseStartsWith(String... value) {
        addStartsWith(QueryColumns.PHRASE, value);
        return this;
    }

    public QuerySelection phraseEndsWith(String... value) {
        addEndsWith(QueryColumns.PHRASE, value);
        return this;
    }

    public QuerySelection orderByPhrase(boolean desc) {
        orderBy(QueryColumns.PHRASE, desc);
        return this;
    }

    public QuerySelection orderByPhrase() {
        orderBy(QueryColumns.PHRASE, false);
        return this;
    }
}
