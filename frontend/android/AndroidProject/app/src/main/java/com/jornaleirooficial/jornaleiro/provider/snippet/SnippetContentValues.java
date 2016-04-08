package com.jornaleirooficial.jornaleiro.provider.snippet;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code snippet} table.
 */
public class SnippetContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return SnippetColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable SnippetSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable SnippetSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public SnippetContentValues putQueryId(@Nullable Integer value) {
        mContentValues.put(SnippetColumns.QUERY_ID, value);
        return this;
    }

    public SnippetContentValues putQueryIdNull() {
        mContentValues.putNull(SnippetColumns.QUERY_ID);
        return this;
    }

    public SnippetContentValues putSessionId(@Nullable Integer value) {
        mContentValues.put(SnippetColumns.SESSION_ID, value);
        return this;
    }

    public SnippetContentValues putSessionIdNull() {
        mContentValues.putNull(SnippetColumns.SESSION_ID);
        return this;
    }

    public SnippetContentValues putPage(@Nullable Integer value) {
        mContentValues.put(SnippetColumns.PAGE, value);
        return this;
    }

    public SnippetContentValues putPageNull() {
        mContentValues.putNull(SnippetColumns.PAGE);
        return this;
    }

    public SnippetContentValues putDate(@Nullable Date value) {
        mContentValues.put(SnippetColumns.DATE, value == null ? null : value.getTime());
        return this;
    }

    public SnippetContentValues putDateNull() {
        mContentValues.putNull(SnippetColumns.DATE);
        return this;
    }

    public SnippetContentValues putDate(@Nullable Long value) {
        mContentValues.put(SnippetColumns.DATE, value);
        return this;
    }

    public SnippetContentValues putContent(@Nullable String value) {
        mContentValues.put(SnippetColumns.CONTENT, value);
        return this;
    }

    public SnippetContentValues putContentNull() {
        mContentValues.putNull(SnippetColumns.CONTENT);
        return this;
    }

    public SnippetContentValues putDocumentId(@Nullable Integer value) {
        mContentValues.put(SnippetColumns.DOCUMENT_ID, value);
        return this;
    }

    public SnippetContentValues putDocumentIdNull() {
        mContentValues.putNull(SnippetColumns.DOCUMENT_ID);
        return this;
    }
}
