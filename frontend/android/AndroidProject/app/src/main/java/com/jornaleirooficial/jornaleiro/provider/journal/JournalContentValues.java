package com.jornaleirooficial.jornaleiro.provider.journal;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code journal} table.
 */
public class JournalContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return JournalColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable JournalSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable JournalSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public JournalContentValues putName(@Nullable String value) {
        mContentValues.put(JournalColumns.NAME, value);
        return this;
    }

    public JournalContentValues putNameNull() {
        mContentValues.putNull(JournalColumns.NAME);
        return this;
    }
}
