package com.example.usamanaseer.googlemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="LANGUAGES";
    //tables
    private static final String tbl_lang="Lang";

    //store table fields
    private static final String LangName="Name";
    private static final String LangCode="Code";


    private static final String CREATE_LANG_TABLE="CREATE TABLE "+tbl_lang+" ("+LangName+" TEXT PRIMARY KEY, "+
            LangCode+" TEXT )";

    public DatabaseHandler(Context context){//, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);//factory
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_LANG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tbl_lang);
        onCreate(db);

    }

    // Adding new store
    public void addLang(LanguageWithCode LangData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LangName,LangData.getName());
        values.put(LangCode, LangData.getCode()); // Contact Name

        // Inserting Row
        db.insert(tbl_lang, null, values);
        db.close(); // Closing database connection
    }

    // Getting All stores
    public List<LanguageWithCode> getAllStores() {
        List<LanguageWithCode> storeList = new ArrayList<LanguageWithCode>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + tbl_lang+" ORDER BY "+LangName+" ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LanguageWithCode record = new LanguageWithCode();
                record.setName(cursor.getString(0));//(Integer.parseInt(cursor.getString(0)));
                record.setCode(cursor.getString(1));  //(cursor.getString(1));

                storeList.add(record);
            } while (cursor.moveToNext());
        }

        // return contact list
        return storeList;
    }


    // Getting max store id
    public String getLanguageCode(String zuban) {
        SQLiteDatabase db = this.getWritableDatabase();
        onOpen(db);
        String id="";
        String maxQuery = "SELECT "+LangCode+" FROM " + tbl_lang+" WHERE Name="+"\'"+zuban+"\'";

        //Cursor cursor=db.query(tbl_lang, new String [] {"MAX("+storeId+")"}, null, null, null, null, null);
        Cursor cursor = db.rawQuery(maxQuery, null);
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                id = cursor.getString(0); //(cursor.getColumnIndex("maximum")); //getString(0); //getInt(0); //(mCursor.getColumnIndex(MY_QUERY));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            close(); //closeDB();
        }
        return id;
    }

    /*// Deleting single offer
    public void deleteFav(String fm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tbl_fav, f_offerId + " = ?",
                new String[] {fm });
        db.close();
    }*/


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    @Override
    public synchronized void close() {
        super.close();
    }



}
