package nano.amr.www.photohub.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amr on 12/20/17.
 */

public class PhotoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "phototable.db";
    private static final int DATABASE_VERSION = 1;


    public PhotoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        PhotoTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        PhotoTable.onUpgrade(db, oldVersion, newVersion);
    }
}
