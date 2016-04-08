package com.jornaleirooficial.jornaleiro.provider.snippet;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code snippet} table.
 */
public class SnippetCursor extends AbstractCursor implements SnippetModel {
    public SnippetCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(SnippetColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code query_id} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getQueryId() {
        Integer res = getIntegerOrNull(SnippetColumns.QUERY_ID);
        return res;
    }

    /**
     * Get the {@code session_id} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getSessionId() {
        Integer res = getIntegerOrNull(SnippetColumns.SESSION_ID);
        return res;
    }

    /**
     * Get the {@code page} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getPage() {
        Integer res = getIntegerOrNull(SnippetColumns.PAGE);
        return res;
    }

    /**
     * Get the {@code date} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getDate() {
        Date res = getDateOrNull(SnippetColumns.DATE);
        return res;
    }

    /**
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getContent() {
        String res = getStringOrNull(SnippetColumns.CONTENT);
        return res;
    }

    /**
     * Get the {@code document_id} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getDocumentId() {
        Integer res = getIntegerOrNull(SnippetColumns.DOCUMENT_ID);
        return res;
    }
}
