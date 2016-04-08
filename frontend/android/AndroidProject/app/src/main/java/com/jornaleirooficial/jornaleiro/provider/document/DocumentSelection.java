package com.jornaleirooficial.jornaleiro.provider.document;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractSelection;

/**
 * Selection for the {@code document} table.
 */
public class DocumentSelection extends AbstractSelection<DocumentSelection> {
    @Override
    protected Uri baseUri() {
        return DocumentColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code DocumentCursor} object, which is positioned before the first entry, or null.
     */
    public DocumentCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new DocumentCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public DocumentCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code DocumentCursor} object, which is positioned before the first entry, or null.
     */
    public DocumentCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new DocumentCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public DocumentCursor query(Context context) {
        return query(context, null);
    }


    public DocumentSelection id(long... value) {
        addEquals("document." + DocumentColumns._ID, toObjectArray(value));
        return this;
    }

    public DocumentSelection idNot(long... value) {
        addNotEquals("document." + DocumentColumns._ID, toObjectArray(value));
        return this;
    }

    public DocumentSelection orderById(boolean desc) {
        orderBy("document." + DocumentColumns._ID, desc);
        return this;
    }

    public DocumentSelection orderById() {
        return orderById(false);
    }

    public DocumentSelection sessionId(Integer... value) {
        addEquals(DocumentColumns.SESSION_ID, value);
        return this;
    }

    public DocumentSelection sessionIdNot(Integer... value) {
        addNotEquals(DocumentColumns.SESSION_ID, value);
        return this;
    }

    public DocumentSelection sessionIdGt(int value) {
        addGreaterThan(DocumentColumns.SESSION_ID, value);
        return this;
    }

    public DocumentSelection sessionIdGtEq(int value) {
        addGreaterThanOrEquals(DocumentColumns.SESSION_ID, value);
        return this;
    }

    public DocumentSelection sessionIdLt(int value) {
        addLessThan(DocumentColumns.SESSION_ID, value);
        return this;
    }

    public DocumentSelection sessionIdLtEq(int value) {
        addLessThanOrEquals(DocumentColumns.SESSION_ID, value);
        return this;
    }

    public DocumentSelection orderBySessionId(boolean desc) {
        orderBy(DocumentColumns.SESSION_ID, desc);
        return this;
    }

    public DocumentSelection orderBySessionId() {
        orderBy(DocumentColumns.SESSION_ID, false);
        return this;
    }

    public DocumentSelection page(Integer... value) {
        addEquals(DocumentColumns.PAGE, value);
        return this;
    }

    public DocumentSelection pageNot(Integer... value) {
        addNotEquals(DocumentColumns.PAGE, value);
        return this;
    }

    public DocumentSelection pageGt(int value) {
        addGreaterThan(DocumentColumns.PAGE, value);
        return this;
    }

    public DocumentSelection pageGtEq(int value) {
        addGreaterThanOrEquals(DocumentColumns.PAGE, value);
        return this;
    }

    public DocumentSelection pageLt(int value) {
        addLessThan(DocumentColumns.PAGE, value);
        return this;
    }

    public DocumentSelection pageLtEq(int value) {
        addLessThanOrEquals(DocumentColumns.PAGE, value);
        return this;
    }

    public DocumentSelection orderByPage(boolean desc) {
        orderBy(DocumentColumns.PAGE, desc);
        return this;
    }

    public DocumentSelection orderByPage() {
        orderBy(DocumentColumns.PAGE, false);
        return this;
    }

    public DocumentSelection date(Date... value) {
        addEquals(DocumentColumns.DATE, value);
        return this;
    }

    public DocumentSelection dateNot(Date... value) {
        addNotEquals(DocumentColumns.DATE, value);
        return this;
    }

    public DocumentSelection date(Long... value) {
        addEquals(DocumentColumns.DATE, value);
        return this;
    }

    public DocumentSelection dateAfter(Date value) {
        addGreaterThan(DocumentColumns.DATE, value);
        return this;
    }

    public DocumentSelection dateAfterEq(Date value) {
        addGreaterThanOrEquals(DocumentColumns.DATE, value);
        return this;
    }

    public DocumentSelection dateBefore(Date value) {
        addLessThan(DocumentColumns.DATE, value);
        return this;
    }

    public DocumentSelection dateBeforeEq(Date value) {
        addLessThanOrEquals(DocumentColumns.DATE, value);
        return this;
    }

    public DocumentSelection orderByDate(boolean desc) {
        orderBy(DocumentColumns.DATE, desc);
        return this;
    }

    public DocumentSelection orderByDate() {
        orderBy(DocumentColumns.DATE, false);
        return this;
    }

    public DocumentSelection title(String... value) {
        addEquals(DocumentColumns.TITLE, value);
        return this;
    }

    public DocumentSelection titleNot(String... value) {
        addNotEquals(DocumentColumns.TITLE, value);
        return this;
    }

    public DocumentSelection titleLike(String... value) {
        addLike(DocumentColumns.TITLE, value);
        return this;
    }

    public DocumentSelection titleContains(String... value) {
        addContains(DocumentColumns.TITLE, value);
        return this;
    }

    public DocumentSelection titleStartsWith(String... value) {
        addStartsWith(DocumentColumns.TITLE, value);
        return this;
    }

    public DocumentSelection titleEndsWith(String... value) {
        addEndsWith(DocumentColumns.TITLE, value);
        return this;
    }

    public DocumentSelection orderByTitle(boolean desc) {
        orderBy(DocumentColumns.TITLE, desc);
        return this;
    }

    public DocumentSelection orderByTitle() {
        orderBy(DocumentColumns.TITLE, false);
        return this;
    }

    public DocumentSelection url(String... value) {
        addEquals(DocumentColumns.URL, value);
        return this;
    }

    public DocumentSelection urlNot(String... value) {
        addNotEquals(DocumentColumns.URL, value);
        return this;
    }

    public DocumentSelection urlLike(String... value) {
        addLike(DocumentColumns.URL, value);
        return this;
    }

    public DocumentSelection urlContains(String... value) {
        addContains(DocumentColumns.URL, value);
        return this;
    }

    public DocumentSelection urlStartsWith(String... value) {
        addStartsWith(DocumentColumns.URL, value);
        return this;
    }

    public DocumentSelection urlEndsWith(String... value) {
        addEndsWith(DocumentColumns.URL, value);
        return this;
    }

    public DocumentSelection orderByUrl(boolean desc) {
        orderBy(DocumentColumns.URL, desc);
        return this;
    }

    public DocumentSelection orderByUrl() {
        orderBy(DocumentColumns.URL, false);
        return this;
    }

    public DocumentSelection content(String... value) {
        addEquals(DocumentColumns.CONTENT, value);
        return this;
    }

    public DocumentSelection contentNot(String... value) {
        addNotEquals(DocumentColumns.CONTENT, value);
        return this;
    }

    public DocumentSelection contentLike(String... value) {
        addLike(DocumentColumns.CONTENT, value);
        return this;
    }

    public DocumentSelection contentContains(String... value) {
        addContains(DocumentColumns.CONTENT, value);
        return this;
    }

    public DocumentSelection contentStartsWith(String... value) {
        addStartsWith(DocumentColumns.CONTENT, value);
        return this;
    }

    public DocumentSelection contentEndsWith(String... value) {
        addEndsWith(DocumentColumns.CONTENT, value);
        return this;
    }

    public DocumentSelection orderByContent(boolean desc) {
        orderBy(DocumentColumns.CONTENT, desc);
        return this;
    }

    public DocumentSelection orderByContent() {
        orderBy(DocumentColumns.CONTENT, false);
        return this;
    }
}
