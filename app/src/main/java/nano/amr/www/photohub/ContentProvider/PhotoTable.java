package nano.amr.www.photohub.ContentProvider;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by amr on 12/20/17.
 */

public class PhotoTable {
    // Database table
    public static final String TABLE_PHOTO = "photo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PATH = "path";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PHOTO
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_PATH + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(PhotoTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTO);
        onCreate(database);
    }
}
