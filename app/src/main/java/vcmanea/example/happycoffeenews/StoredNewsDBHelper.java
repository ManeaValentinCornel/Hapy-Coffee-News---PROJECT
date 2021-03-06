package vcmanea.example.happycoffeenews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import vcmanea.example.happycoffeenews.StoreNewsContract.StoreNewsEntry;

public class StoredNewsDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "StoredNewsDBHelper";
    private static final String DATABASE_NAME = "storeNews.db";
    private static final int DATABASE_VERSION = 1;
    private static StoredNewsDBHelper instance = null;
    private static SQLiteDatabase db;

            //Implement StoredNewsDBHelper as a singleton;
    private StoredNewsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_STORED_NEWS_TABLE = "CREATE TABLE IF NOT EXISTS  " +
                StoreNewsEntry.TABLE_NAME + " (" +
                StoreNewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                StoreNewsEntry.COLUMN_TITLE + " TEXT, " +
                StoreNewsEntry.COLUMN_DESCRIPTION + " TEXT, " +
                StoreNewsEntry.COLUMN_CONTENT + " TEXT, " +
                StoreNewsEntry.COLUMN_IMAGE + " BLOB" +
                ");";
        db.execSQL(SQL_CREATE_STORED_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

     static StoredNewsDBHelper getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new StoredNewsDBHelper(context);
        }
        return instance;
    }
    //****************ADDING DATA USING ContetValues class in forn key-value pairs******************//
    boolean addData(String title,String description,String content,byte[] imageUrl){

        ContentValues cv=new ContentValues();
        cv.put(StoreNewsEntry.COLUMN_TITLE,title);
        cv.put(StoreNewsEntry.COLUMN_DESCRIPTION,description);
        cv.put(StoreNewsEntry.COLUMN_CONTENT,content);
        cv.put(StoreNewsEntry.COLUMN_IMAGE,imageUrl);

        long result=db.insert(StoreNewsEntry.TABLE_NAME,null,cv);
        return(result==-1);

    }

    //****************Querying the data******************//
     static Cursor getAllData() {
        Cursor result = db.rawQuery("SELECT * FROM " + StoreNewsEntry.TABLE_NAME + ";", null);
        Log.d(TAG, "getAllData: " + result.getCount());
        return result;

    }

     String getContent(int i) {
        Cursor result = db.rawQuery("SELECT " + StoreNewsEntry.COLUMN_CONTENT + " FROM " + StoreNewsEntry.TABLE_NAME + " WHERE " + StoreNewsEntry._ID + "=" + i + ";", null);
        result.moveToFirst();
        Log.d(TAG, "getContent: "+ i +result.getString(result.getColumnIndex(StoreNewsEntry.COLUMN_CONTENT)) );
        return result.getString(result.getColumnIndex(StoreNewsEntry.COLUMN_CONTENT));


    }

     void delete(int i){
        db.execSQL("DELETE FROM " + StoreNewsEntry.TABLE_NAME + " WHERE " +StoreNewsEntry._ID+"="+i);

    }
}
