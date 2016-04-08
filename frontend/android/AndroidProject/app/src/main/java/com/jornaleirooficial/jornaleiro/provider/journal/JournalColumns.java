package com.jornaleirooficial.jornaleiro.provider.journal;

import android.net.Uri;
import android.provider.BaseColumns;

import com.jornaleirooficial.jornaleiro.provider.LocalProvider;
import com.jornaleirooficial.jornaleiro.provider.document.DocumentColumns;
import com.jornaleirooficial.jornaleiro.provider.journal.JournalColumns;
import com.jornaleirooficial.jornaleiro.provider.query.QueryColumns;
import com.jornaleirooficial.jornaleiro.provider.session.SessionColumns;
import com.jornaleirooficial.jornaleiro.provider.snippet.SnippetColumns;

/**
 * Columns for the {@code journal} table.
 */
public class JournalColumns implements BaseColumns {
    public static final String TABLE_NAME = "journal";
    public static final Uri CONTENT_URI = Uri.parse(LocalProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String NAME = "name";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            NAME
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
        }
        return false;
    }

}
