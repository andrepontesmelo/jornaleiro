package com.jornaleirooficial.jornaleiro.provider.session;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractSelection;

/**
 * Selection for the {@code session} table.
 */
public class SessionSelection extends AbstractSelection<SessionSelection> {
    @Override
    protected Uri baseUri() {
        return SessionColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code SessionCursor} object, which is positioned before the first entry, or null.
     */
    public SessionCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new SessionCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public SessionCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code SessionCursor} object, which is positioned before the first entry, or null.
     */
    public SessionCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new SessionCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public SessionCursor query(Context context) {
        return query(context, null);
    }


    public SessionSelection id(long... value) {
        addEquals("session." + SessionColumns._ID, toObjectArray(value));
        return this;
    }

    public SessionSelection idNot(long... value) {
        addNotEquals("session." + SessionColumns._ID, toObjectArray(value));
        return this;
    }

    public SessionSelection orderById(boolean desc) {
        orderBy("session." + SessionColumns._ID, desc);
        return this;
    }

    public SessionSelection orderById() {
        return orderById(false);
    }

    public SessionSelection title(String... value) {
        addEquals(SessionColumns.TITLE, value);
        return this;
    }

    public SessionSelection titleNot(String... value) {
        addNotEquals(SessionColumns.TITLE, value);
        return this;
    }

    public SessionSelection titleLike(String... value) {
        addLike(SessionColumns.TITLE, value);
        return this;
    }

    public SessionSelection titleContains(String... value) {
        addContains(SessionColumns.TITLE, value);
        return this;
    }

    public SessionSelection titleStartsWith(String... value) {
        addStartsWith(SessionColumns.TITLE, value);
        return this;
    }

    public SessionSelection titleEndsWith(String... value) {
        addEndsWith(SessionColumns.TITLE, value);
        return this;
    }

    public SessionSelection orderByTitle(boolean desc) {
        orderBy(SessionColumns.TITLE, desc);
        return this;
    }

    public SessionSelection orderByTitle() {
        orderBy(SessionColumns.TITLE, false);
        return this;
    }

    public SessionSelection journalId(Integer... value) {
        addEquals(SessionColumns.JOURNAL_ID, value);
        return this;
    }

    public SessionSelection journalIdNot(Integer... value) {
        addNotEquals(SessionColumns.JOURNAL_ID, value);
        return this;
    }

    public SessionSelection journalIdGt(int value) {
        addGreaterThan(SessionColumns.JOURNAL_ID, value);
        return this;
    }

    public SessionSelection journalIdGtEq(int value) {
        addGreaterThanOrEquals(SessionColumns.JOURNAL_ID, value);
        return this;
    }

    public SessionSelection journalIdLt(int value) {
        addLessThan(SessionColumns.JOURNAL_ID, value);
        return this;
    }

    public SessionSelection journalIdLtEq(int value) {
        addLessThanOrEquals(SessionColumns.JOURNAL_ID, value);
        return this;
    }

    public SessionSelection orderByJournalId(boolean desc) {
        orderBy(SessionColumns.JOURNAL_ID, desc);
        return this;
    }

    public SessionSelection orderByJournalId() {
        orderBy(SessionColumns.JOURNAL_ID, false);
        return this;
    }
}
