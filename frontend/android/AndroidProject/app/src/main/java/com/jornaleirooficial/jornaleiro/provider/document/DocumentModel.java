package com.jornaleirooficial.jornaleiro.provider.document;

import com.jornaleirooficial.jornaleiro.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code document} table.
 */
public interface DocumentModel extends BaseModel {

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
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Get the {@code url} value.
     * Can be {@code null}.
     */
    @Nullable
    String getUrl();

    /**
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    @Nullable
    String getContent();
}
