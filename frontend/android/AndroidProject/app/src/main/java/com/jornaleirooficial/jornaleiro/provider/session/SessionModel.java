package com.jornaleirooficial.jornaleiro.provider.session;

import com.jornaleirooficial.jornaleiro.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
