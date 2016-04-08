package com.jornaleirooficial.jornaleiro.provider.query;

import android.net.Uri;
import android.provider.BaseColumns;

import com.jornaleirooficial.jornaleiro.provider.LocalProvider;
import com.jornaleirooficial.jornaleiro.provider.document.DocumentColumns;
import com.jornaleirooficial.jornaleiro.provider.journal.JournalColumns;
import com.jornaleirooficial.jornaleiro.provider.query.QueryColumns;
import com.jornaleirooficial.jornaleiro.provider.session.SessionColumns;
import com.jornaleirooficial.jornaleiro.provider.snippet.SnippetColumns;

/**
 * Columns for the {@code query} table.
 */
public class QueryColumns implements BaseColumns {
    public static final String TABLE_NAME = "query";
    public static final Uri CONTENT_URI = Uri.parse(LocalProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String PHRASE = "phrase";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            PHRASE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(PHRASE) || c.contains("." + PHRASE)) return true;
        }
        return false;
    }

}
