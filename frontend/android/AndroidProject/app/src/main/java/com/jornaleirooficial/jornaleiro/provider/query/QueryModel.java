package com.jornaleirooficial.jornaleiro.provider.query;

import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.BaseModel;

/**
 * Data model for the {@code query} table.
 */
public interface QueryModel extends BaseModel {

    /**
     * Get the {@code phrase} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPhrase();
}
