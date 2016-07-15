package com.jornaleirooficial.jornaleiro.provider.session;

import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.BaseModel;

/**
 * Data model for the {@code session} table.
 */
public interface SessionModel extends BaseModel {

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Get the {@code journal_id} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getJournalId();
}
