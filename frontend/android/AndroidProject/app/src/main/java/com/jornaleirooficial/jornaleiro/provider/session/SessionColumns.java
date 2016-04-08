package com.jornaleirooficial.jornaleiro.provider.session;

import android.net.Uri;
import android.provider.BaseColumns;

import com.jornaleirooficial.jornaleiro.provider.LocalProvider;
import com.jornaleirooficial.jornaleiro.provider.document.DocumentColumns;
import com.jornaleirooficial.jornaleiro.provider.journal.JournalColumns;
import com.jornaleirooficial.jornaleiro.provider.query.QueryColumns;
import com.jornaleirooficial.jornaleiro.provider.session.SessionColumns;
import com.jornaleirooficial.jornaleiro.provider.snippet.SnippetColumns;

/**
 * Columns for the {@code session} table.
 */
public class SessionColumns implements BaseColumns {
    public static final String TABLE_NAME = "session";
    public static final Uri CONTENT_URI = Uri.parse(LocalProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String TITLE = "title";

    public static final String JOURNAL_ID = "journal_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            JOURNAL_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(JOURNAL_ID) || c.contains("." + JOURNAL_ID)) return true;
        }
        return false;
    }

}
