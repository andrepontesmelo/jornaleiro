package com.jornaleirooficial.jornaleiro.provider.journal;

import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.BaseModel;

/**
 * Data model for the {@code journal} table.
 */
public interface JournalModel extends BaseModel {

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getName();
}
