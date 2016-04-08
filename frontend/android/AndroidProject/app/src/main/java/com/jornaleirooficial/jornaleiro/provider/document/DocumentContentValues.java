package com.jornaleirooficial.jornaleiro.provider.document;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jornaleirooficial.jornaleiro.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code document} table.
 */
public class DocumentContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return DocumentColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable DocumentSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable DocumentSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public DocumentContentValues putSessionId(@Nullable Integer value) {
        mContentValues.put(DocumentColumns.SESSION_ID, value);
        return this;
    }

    public DocumentContentValues putSessionIdNull() {
        mContentValues.putNull(DocumentColumns.SESSION_ID);
        return this;
    }

    public DocumentContentValues putPage(@Nullable Integer value) {
        mContentValues.put(DocumentColumns.PAGE, value);
        return this;
    }

    public DocumentContentValues putPageNull() {
        mContentValues.putNull(DocumentColumns.PAGE);
        return this;
    }

    public DocumentContentValues putDate(@Nullable Date value) {
        mContentValues.put(DocumentColumns.DATE, value == null ? null : value.getTime());
        return this;
    }

    public DocumentContentValues putDateNull() {
        mContentValues.putNull(DocumentColumns.DATE);
        return this;
    }

    public DocumentContentValues putDate(@Nullable Long value) {
        mContentValues.put(DocumentColumns.DATE, value);
        return this;
    }

    public DocumentContentValues putTitle(@Nullable String value) {
        mContentValues.put(DocumentColumns.TITLE, value);
        return this;
    }

    public DocumentContentValues putTitleNull() {
        mContentValues.putNull(DocumentColumns.TITLE);
        return this;
    }

    public DocumentContentValues putUrl(@Nullable String value) {
        mContentValues.put(DocumentColumns.URL, value);
        return this;
    }

    public DocumentContentValues putUrlNull() {
        mContentValues.putNull(DocumentColumns.URL);
        return this;
    }

    public DocumentContentValues putContent(@Nullable String value) {
        mContentValues.put(DocumentColumns.CONTENT, value);
        return this;
    }

    public DocumentContentValues putContentNull() {
        mContentValues.putNull(DocumentColumns.CONTENT);
        return this;
    }
}
