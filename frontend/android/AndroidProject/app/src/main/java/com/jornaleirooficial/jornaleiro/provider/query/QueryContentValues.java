package com.jornaleirooficial.jornaleiro.provider.query;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code query} table.
 */
public class QueryContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return QueryColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable QuerySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable QuerySelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public QueryContentValues putPhrase(@Nullable String value) {
        mContentValues.put(QueryColumns.PHRASE, value);
        return this;
    }

    public QueryContentValues putPhraseNull() {
        mContentValues.putNull(QueryColumns.PHRASE);
        return this;
    }
}
