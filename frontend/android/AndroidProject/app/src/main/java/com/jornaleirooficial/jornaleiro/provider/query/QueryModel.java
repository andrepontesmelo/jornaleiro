package com.jornaleirooficial.jornaleiro.provider.query;

import com.jornaleirooficial.jornaleiro.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
