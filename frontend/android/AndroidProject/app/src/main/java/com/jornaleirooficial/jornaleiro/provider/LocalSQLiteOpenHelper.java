package com.jornaleirooficial.jornaleiro.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.jornaleirooficial.jornaleiro.BuildConfig;
import com.jornaleirooficial.jornaleiro.provider.document.DocumentColumns;
import com.jornaleirooficial.jornaleiro.provider.journal.JournalColumns;
import com.jornaleirooficial.jornaleiro.provider.query.QueryColumns;
import com.jornaleirooficial.jornaleiro.provider.session.SessionColumns;
import com.jornaleirooficial.jornaleiro.provider.snippet.SnippetColumns;

public class LocalSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = LocalSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "jornaleiro.db";
    private static final int DATABASE_VERSION = 1;
    private static LocalSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final LocalSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_DOCUMENT = "CREATE TABLE IF NOT EXISTS "
            + DocumentColumns.TABLE_NAME + " ( "
            + DocumentColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DocumentColumns.SESSION_ID + " INTEGER, "
            + DocumentColumns.PAGE + " INTEGER, "
            + DocumentColumns.DATE + " INTEGER, "
            + DocumentColumns.TITLE + " TEXT, "
            + DocumentColumns.URL + " TEXT, "
            + DocumentColumns.CONTENT + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_JOURNAL = "CREATE TABLE IF NOT EXISTS "
            + JournalColumns.TABLE_NAME + " ( "
            + JournalColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + JournalColumns.NAME + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS "
            + QueryColumns.TABLE_NAME + " ( "
            + QueryColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QueryColumns.PHRASE + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_SESSION = "CREATE TABLE IF NOT EXISTS "
            + SessionColumns.TABLE_NAME + " ( "
            + SessionColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SessionColumns.TITLE + " TEXT, "
            + SessionColumns.JOURNAL_ID + " INTEGER "
            + " );";

    public static final String SQL_CREATE_TABLE_SNIPPET = "CREATE TABLE IF NOT EXISTS "
            + SnippetColumns.TABLE_NAME + " ( "
            + SnippetColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SnippetColumns.QUERY_ID + " INTEGER, "
            + SnippetColumns.SESSION_ID + " INTEGER, "
            + SnippetColumns.PAGE + " INTEGER, "
            + SnippetColumns.DATE + " INTEGER, "
            + SnippetColumns.CONTENT + " TEXT, "
            + SnippetColumns.DOCUMENT_ID + " INTEGER "
            + " );";

    // @formatter:on

    public static LocalSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static LocalSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static LocalSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new LocalSQLiteOpenHelper(context);
    }

    private LocalSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new LocalSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static LocalSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new LocalSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private LocalSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new LocalSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_DOCUMENT);
        db.execSQL(SQL_CREATE_TABLE_JOURNAL);
        db.execSQL(SQL_CREATE_TABLE_QUERY);
        db.execSQL(SQL_CREATE_TABLE_SESSION);
        db.execSQL(SQL_CREATE_TABLE_SNIPPET);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
