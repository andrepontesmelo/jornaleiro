package com.jornaleirooficial.jornaleiro.provider.snippet;

import android.net.Uri;
import android.provider.BaseColumns;

import com.jornaleirooficial.jornaleiro.provider.LocalProvider;

/**
 * Columns for the {@code snippet} table.
 */
public class SnippetColumns implements BaseColumns {
    public static final String TABLE_NAME = "snippet";
    public static final Uri CONTENT_URI = Uri.parse(LocalProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String QUERY_ID = "query_id";

    public static final String SESSION_ID = "session_id";

    public static final String PAGE = "page";

    public static final String DATE = "date";

    public static final String CONTENT = "content";

    public static final String DOCUMENT_ID = "document_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            QUERY_ID,
            SESSION_ID,
            PAGE,
            DATE,
            CONTENT,
            DOCUMENT_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(QUERY_ID) || c.contains("." + QUERY_ID)) return true;
            if (c.equals(SESSION_ID) || c.contains("." + SESSION_ID)) return true;
            if (c.equals(PAGE) || c.contains("." + PAGE)) return true;
            if (c.equals(DATE) || c.contains("." + DATE)) return true;
            if (c.equals(CONTENT) || c.contains("." + CONTENT)) return true;
            if (c.equals(DOCUMENT_ID) || c.contains("." + DOCUMENT_ID)) return true;
        }
        return false;
    }

}
