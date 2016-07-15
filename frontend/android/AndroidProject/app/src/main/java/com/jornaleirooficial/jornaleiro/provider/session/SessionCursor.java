package com.jornaleirooficial.jornaleiro.provider.session;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code session} table.
 */
public class SessionCursor extends AbstractCursor implements SessionModel {
    public SessionCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(SessionColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(SessionColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code journal_id} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getJournalId() {
        Integer res = getIntegerOrNull(SessionColumns.JOURNAL_ID);
        return res;
    }
}
