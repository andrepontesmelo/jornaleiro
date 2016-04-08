package com.jornaleirooficial.jornaleiro.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jornaleirooficial.jornaleiro.BuildConfig;
import com.jornaleirooficial.jornaleiro.provider.base.BaseContentProvider;
import com.jornaleirooficial.jornaleiro.provider.document.DocumentColumns;
import com.jornaleirooficial.jornaleiro.provider.journal.JournalColumns;
import com.jornaleirooficial.jornaleiro.provider.query.QueryColumns;
import com.jornaleirooficial.jornaleiro.provider.session.SessionColumns;
import com.jornaleirooficial.jornaleiro.provider.snippet.SnippetColumns;

public class LocalProvider extends BaseContentProvider {
    private static final String TAG = LocalProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.example.app.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_DOCUMENT = 0;
    private static final int URI_TYPE_DOCUMENT_ID = 1;

    private static final int URI_TYPE_JOURNAL = 2;
    private static final int URI_TYPE_JOURNAL_ID = 3;

    private static final int URI_TYPE_QUERY = 4;
    private static final int URI_TYPE_QUERY_ID = 5;

    private static final int URI_TYPE_SESSION = 6;
    private static final int URI_TYPE_SESSION_ID = 7;

    private static final int URI_TYPE_SNIPPET = 8;
    private static final int URI_TYPE_SNIPPET_ID = 9;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, DocumentColumns.TABLE_NAME, URI_TYPE_DOCUMENT);
        URI_MATCHER.addURI(AUTHORITY, DocumentColumns.TABLE_NAME + "/#", URI_TYPE_DOCUMENT_ID);
        URI_MATCHER.addURI(AUTHORITY, JournalColumns.TABLE_NAME, URI_TYPE_JOURNAL);
        URI_MATCHER.addURI(AUTHORITY, JournalColumns.TABLE_NAME + "/#", URI_TYPE_JOURNAL_ID);
        URI_MATCHER.addURI(AUTHORITY, QueryColumns.TABLE_NAME, URI_TYPE_QUERY);
        URI_MATCHER.addURI(AUTHORITY, QueryColumns.TABLE_NAME + "/#", URI_TYPE_QUERY_ID);
        URI_MATCHER.addURI(AUTHORITY, SessionColumns.TABLE_NAME, URI_TYPE_SESSION);
        URI_MATCHER.addURI(AUTHORITY, SessionColumns.TABLE_NAME + "/#", URI_TYPE_SESSION_ID);
        URI_MATCHER.addURI(AUTHORITY, SnippetColumns.TABLE_NAME, URI_TYPE_SNIPPET);
        URI_MATCHER.addURI(AUTHORITY, SnippetColumns.TABLE_NAME + "/#", URI_TYPE_SNIPPET_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return LocalSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_DOCUMENT:
                return TYPE_CURSOR_DIR + DocumentColumns.TABLE_NAME;
            case URI_TYPE_DOCUMENT_ID:
                return TYPE_CURSOR_ITEM + DocumentColumns.TABLE_NAME;

            case URI_TYPE_JOURNAL:
                return TYPE_CURSOR_DIR + JournalColumns.TABLE_NAME;
            case URI_TYPE_JOURNAL_ID:
                return TYPE_CURSOR_ITEM + JournalColumns.TABLE_NAME;

            case URI_TYPE_QUERY:
                return TYPE_CURSOR_DIR + QueryColumns.TABLE_NAME;
            case URI_TYPE_QUERY_ID:
                return TYPE_CURSOR_ITEM + QueryColumns.TABLE_NAME;

            case URI_TYPE_SESSION:
                return TYPE_CURSOR_DIR + SessionColumns.TABLE_NAME;
            case URI_TYPE_SESSION_ID:
                return TYPE_CURSOR_ITEM + SessionColumns.TABLE_NAME;

            case URI_TYPE_SNIPPET:
                return TYPE_CURSOR_DIR + SnippetColumns.TABLE_NAME;
            case URI_TYPE_SNIPPET_ID:
                return TYPE_CURSOR_ITEM + SnippetColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_DOCUMENT:
            case URI_TYPE_DOCUMENT_ID:
                res.table = DocumentColumns.TABLE_NAME;
                res.idColumn = DocumentColumns._ID;
                res.tablesWithJoins = DocumentColumns.TABLE_NAME;
                res.orderBy = DocumentColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_JOURNAL:
            case URI_TYPE_JOURNAL_ID:
                res.table = JournalColumns.TABLE_NAME;
                res.idColumn = JournalColumns._ID;
                res.tablesWithJoins = JournalColumns.TABLE_NAME;
                res.orderBy = JournalColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_QUERY:
            case URI_TYPE_QUERY_ID:
                res.table = QueryColumns.TABLE_NAME;
                res.idColumn = QueryColumns._ID;
                res.tablesWithJoins = QueryColumns.TABLE_NAME;
                res.orderBy = QueryColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_SESSION:
            case URI_TYPE_SESSION_ID:
                res.table = SessionColumns.TABLE_NAME;
                res.idColumn = SessionColumns._ID;
                res.tablesWithJoins = SessionColumns.TABLE_NAME;
                res.orderBy = SessionColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_SNIPPET:
            case URI_TYPE_SNIPPET_ID:
                res.table = SnippetColumns.TABLE_NAME;
                res.idColumn = SnippetColumns._ID;
                res.tablesWithJoins = SnippetColumns.TABLE_NAME;
                res.orderBy = SnippetColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_DOCUMENT_ID:
            case URI_TYPE_JOURNAL_ID:
            case URI_TYPE_QUERY_ID:
            case URI_TYPE_SESSION_ID:
            case URI_TYPE_SNIPPET_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
