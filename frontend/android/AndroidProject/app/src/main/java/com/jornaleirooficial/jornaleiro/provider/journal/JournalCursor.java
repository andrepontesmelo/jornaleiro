package com.jornaleirooficial.jornaleiro.provider.journal;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code journal} table.
 */
public class JournalCursor extends AbstractCursor implements JournalModel {
    public JournalCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(JournalColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(JournalColumns.NAME);
        return res;
    }
}
