package com.jornaleirooficial.jornaleiro.provider.document;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code document} table.
 */
public class DocumentCursor extends AbstractCursor implements DocumentModel {
    public DocumentCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(DocumentColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code session_id} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getSessionId() {
        Integer res = getIntegerOrNull(DocumentColumns.SESSION_ID);
        return res;
    }

    /**
     * Get the {@code page} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getPage() {
        Integer res = getIntegerOrNull(DocumentColumns.PAGE);
        return res;
    }

    /**
     * Get the {@code date} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getDate() {
        Date res = getDateOrNull(DocumentColumns.DATE);
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(DocumentColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getUrl() {
        String res = getStringOrNull(DocumentColumns.URL);
        return res;
    }

    /**
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getContent() {
        String res = getStringOrNull(DocumentColumns.CONTENT);
        return res;
    }
}
