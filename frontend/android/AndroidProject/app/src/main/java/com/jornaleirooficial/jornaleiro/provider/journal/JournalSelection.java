package com.jornaleirooficial.jornaleiro.provider.journal;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractSelection;

/**
 * Selection for the {@code journal} table.
 */
public class JournalSelection extends AbstractSelection<JournalSelection> {
    @Override
    protected Uri baseUri() {
        return JournalColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code JournalCursor} object, which is positioned before the first entry, or null.
     */
    public JournalCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new JournalCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public JournalCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code JournalCursor} object, which is positioned before the first entry, or null.
     */
    public JournalCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new JournalCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public JournalCursor query(Context context) {
        return query(context, null);
    }


    public JournalSelection id(long... value) {
        addEquals("journal." + JournalColumns._ID, toObjectArray(value));
        return this;
    }

    public JournalSelection idNot(long... value) {
        addNotEquals("journal." + JournalColumns._ID, toObjectArray(value));
        return this;
    }

    public JournalSelection orderById(boolean desc) {
        orderBy("journal." + JournalColumns._ID, desc);
        return this;
    }

    public JournalSelection orderById() {
        return orderById(false);
    }

    public JournalSelection name(String... value) {
        addEquals(JournalColumns.NAME, value);
        return this;
    }

    public JournalSelection nameNot(String... value) {
        addNotEquals(JournalColumns.NAME, value);
        return this;
    }

    public JournalSelection nameLike(String... value) {
        addLike(JournalColumns.NAME, value);
        return this;
    }

    public JournalSelection nameContains(String... value) {
        addContains(JournalColumns.NAME, value);
        return this;
    }

    public JournalSelection nameStartsWith(String... value) {
        addStartsWith(JournalColumns.NAME, value);
        return this;
    }

    public JournalSelection nameEndsWith(String... value) {
        addEndsWith(JournalColumns.NAME, value);
        return this;
    }

    public JournalSelection orderByName(boolean desc) {
        orderBy(JournalColumns.NAME, desc);
        return this;
    }

    public JournalSelection orderByName() {
        orderBy(JournalColumns.NAME, false);
        return this;
    }
}
