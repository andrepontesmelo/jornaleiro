package com.jornaleirooficial.jornaleiro.provider.query;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code query} table.
 */
public class QueryCursor extends AbstractCursor implements QueryModel {
    public QueryCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(QueryColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code phrase} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPhrase() {
        String res = getStringOrNull(QueryColumns.PHRASE);
        return res;
    }
}
