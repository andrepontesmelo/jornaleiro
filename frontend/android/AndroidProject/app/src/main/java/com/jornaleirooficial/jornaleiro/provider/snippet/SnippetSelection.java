package com.jornaleirooficial.jornaleiro.provider.snippet;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractSelection;

/**
 * Selection for the {@code snippet} table.
 */
public class SnippetSelection extends AbstractSelection<SnippetSelection> {
    @Override
    protected Uri baseUri() {
        return SnippetColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code SnippetCursor} object, which is positioned before the first entry, or null.
     */
    public SnippetCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new SnippetCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public SnippetCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code SnippetCursor} object, which is positioned before the first entry, or null.
     */
    public SnippetCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new SnippetCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public SnippetCursor query(Context context) {
        return query(context, null);
    }


    public SnippetSelection id(long... value) {
        addEquals("snippet." + SnippetColumns._ID, toObjectArray(value));
        return this;
    }

    public SnippetSelection idNot(long... value) {
        addNotEquals("snippet." + SnippetColumns._ID, toObjectArray(value));
        return this;
    }

    public SnippetSelection orderById(boolean desc) {
        orderBy("snippet." + SnippetColumns._ID, desc);
        return this;
    }

    public SnippetSelection orderById() {
        return orderById(false);
    }

    public SnippetSelection queryId(Integer... value) {
        addEquals(SnippetColumns.QUERY_ID, value);
        return this;
    }

    public SnippetSelection queryIdNot(Integer... value) {
        addNotEquals(SnippetColumns.QUERY_ID, value);
        return this;
    }

    public SnippetSelection queryIdGt(int value) {
        addGreaterThan(SnippetColumns.QUERY_ID, value);
        return this;
    }

    public SnippetSelection queryIdGtEq(int value) {
        addGreaterThanOrEquals(SnippetColumns.QUERY_ID, value);
        return this;
    }

    public SnippetSelection queryIdLt(int value) {
        addLessThan(SnippetColumns.QUERY_ID, value);
        return this;
    }

    public SnippetSelection queryIdLtEq(int value) {
        addLessThanOrEquals(SnippetColumns.QUERY_ID, value);
        return this;
    }

    public SnippetSelection orderByQueryId(boolean desc) {
        orderBy(SnippetColumns.QUERY_ID, desc);
        return this;
    }

    public SnippetSelection orderByQueryId() {
        orderBy(SnippetColumns.QUERY_ID, false);
        return this;
    }

    public SnippetSelection sessionId(Integer... value) {
        addEquals(SnippetColumns.SESSION_ID, value);
        return this;
    }

    public SnippetSelection sessionIdNot(Integer... value) {
        addNotEquals(SnippetColumns.SESSION_ID, value);
        return this;
    }

    public SnippetSelection sessionIdGt(int value) {
        addGreaterThan(SnippetColumns.SESSION_ID, value);
        return this;
    }

    public SnippetSelection sessionIdGtEq(int value) {
        addGreaterThanOrEquals(SnippetColumns.SESSION_ID, value);
        return this;
    }

    public SnippetSelection sessionIdLt(int value) {
        addLessThan(SnippetColumns.SESSION_ID, value);
        return this;
    }

    public SnippetSelection sessionIdLtEq(int value) {
        addLessThanOrEquals(SnippetColumns.SESSION_ID, value);
        return this;
    }

    public SnippetSelection orderBySessionId(boolean desc) {
        orderBy(SnippetColumns.SESSION_ID, desc);
        return this;
    }

    public SnippetSelection orderBySessionId() {
        orderBy(SnippetColumns.SESSION_ID, false);
        return this;
    }

    public SnippetSelection page(Integer... value) {
        addEquals(SnippetColumns.PAGE, value);
        return this;
    }

    public SnippetSelection pageNot(Integer... value) {
        addNotEquals(SnippetColumns.PAGE, value);
        return this;
    }

    public SnippetSelection pageGt(int value) {
        addGreaterThan(SnippetColumns.PAGE, value);
        return this;
    }

    public SnippetSelection pageGtEq(int value) {
        addGreaterThanOrEquals(SnippetColumns.PAGE, value);
        return this;
    }

    public SnippetSelection pageLt(int value) {
        addLessThan(SnippetColumns.PAGE, value);
        return this;
    }

    public SnippetSelection pageLtEq(int value) {
        addLessThanOrEquals(SnippetColumns.PAGE, value);
        return this;
    }

    public SnippetSelection orderByPage(boolean desc) {
        orderBy(SnippetColumns.PAGE, desc);
        return this;
    }

    public SnippetSelection orderByPage() {
        orderBy(SnippetColumns.PAGE, false);
        return this;
    }

    public SnippetSelection date(Date... value) {
        addEquals(SnippetColumns.DATE, value);
        return this;
    }

    public SnippetSelection dateNot(Date... value) {
        addNotEquals(SnippetColumns.DATE, value);
        return this;
    }

    public SnippetSelection date(Long... value) {
        addEquals(SnippetColumns.DATE, value);
        return this;
    }

    public SnippetSelection dateAfter(Date value) {
        addGreaterThan(SnippetColumns.DATE, value);
        return this;
    }

    public SnippetSelection dateAfterEq(Date value) {
        addGreaterThanOrEquals(SnippetColumns.DATE, value);
        return this;
    }

    public SnippetSelection dateBefore(Date value) {
        addLessThan(SnippetColumns.DATE, value);
        return this;
    }

    public SnippetSelection dateBeforeEq(Date value) {
        addLessThanOrEquals(SnippetColumns.DATE, value);
        return this;
    }

    public SnippetSelection orderByDate(boolean desc) {
        orderBy(SnippetColumns.DATE, desc);
        return this;
    }

    public SnippetSelection orderByDate() {
        orderBy(SnippetColumns.DATE, false);
        return this;
    }

    public SnippetSelection content(String... value) {
        addEquals(SnippetColumns.CONTENT, value);
        return this;
    }

    public SnippetSelection contentNot(String... value) {
        addNotEquals(SnippetColumns.CONTENT, value);
        return this;
    }

    public SnippetSelection contentLike(String... value) {
        addLike(SnippetColumns.CONTENT, value);
        return this;
    }

    public SnippetSelection contentContains(String... value) {
        addContains(SnippetColumns.CONTENT, value);
        return this;
    }

    public SnippetSelection contentStartsWith(String... value) {
        addStartsWith(SnippetColumns.CONTENT, value);
        return this;
    }

    public SnippetSelection contentEndsWith(String... value) {
        addEndsWith(SnippetColumns.CONTENT, value);
        return this;
    }

    public SnippetSelection orderByContent(boolean desc) {
        orderBy(SnippetColumns.CONTENT, desc);
        return this;
    }

    public SnippetSelection orderByContent() {
        orderBy(SnippetColumns.CONTENT, false);
        return this;
    }

    public SnippetSelection documentId(Integer... value) {
        addEquals(SnippetColumns.DOCUMENT_ID, value);
        return this;
    }

    public SnippetSelection documentIdNot(Integer... value) {
        addNotEquals(SnippetColumns.DOCUMENT_ID, value);
        return this;
    }

    public SnippetSelection documentIdGt(int value) {
        addGreaterThan(SnippetColumns.DOCUMENT_ID, value);
        return this;
    }

    public SnippetSelection documentIdGtEq(int value) {
        addGreaterThanOrEquals(SnippetColumns.DOCUMENT_ID, value);
        return this;
    }

    public SnippetSelection documentIdLt(int value) {
        addLessThan(SnippetColumns.DOCUMENT_ID, value);
        return this;
    }

    public SnippetSelection documentIdLtEq(int value) {
        addLessThanOrEquals(SnippetColumns.DOCUMENT_ID, value);
        return this;
    }

    public SnippetSelection orderByDocumentId(boolean desc) {
        orderBy(SnippetColumns.DOCUMENT_ID, desc);
        return this;
    }

    public SnippetSelection orderByDocumentId() {
        orderBy(SnippetColumns.DOCUMENT_ID, false);
        return this;
    }
}
