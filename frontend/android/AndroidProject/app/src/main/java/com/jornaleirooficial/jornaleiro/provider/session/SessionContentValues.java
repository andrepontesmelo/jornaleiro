package com.jornaleirooficial.jornaleiro.provider.session;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code session} table.
 */
public class SessionContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return SessionColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable SessionSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable SessionSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public SessionContentValues putTitle(@Nullable String value) {
        mContentValues.put(SessionColumns.TITLE, value);
        return this;
    }

    public SessionContentValues putTitleNull() {
        mContentValues.putNull(SessionColumns.TITLE);
        return this;
    }

    public SessionContentValues putJournalId(@Nullable Integer value) {
        mContentValues.put(SessionColumns.JOURNAL_ID, value);
        return this;
    }

    public SessionContentValues putJournalIdNull() {
        mContentValues.putNull(SessionColumns.JOURNAL_ID);
        return this;
    }
}
