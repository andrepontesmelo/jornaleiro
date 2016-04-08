package com.jornaleirooficial.jornaleiro.provider.journal;

import com.jornaleirooficial.jornaleiro.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
