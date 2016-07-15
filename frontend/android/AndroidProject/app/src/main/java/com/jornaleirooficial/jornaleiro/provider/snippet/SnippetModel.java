package com.jornaleirooficial.jornaleiro.provider.snippet;

import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.BaseModel;

import java.util.Date;

/**
 * Data model for the {@code snippet} table.
 */
public interface SnippetModel extends BaseModel {

    /**
     * Get the {@code query_id} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getQueryId();

    /**
     * Get the {@code session_id} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getSessionId();

    /**
     * Get the {@code page} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getPage();

    /**
     * Get the {@code date} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getDate();

    /**
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    @Nullable
    String getContent();

    /**
     * Get the {@code document_id} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getDocumentId();
}
