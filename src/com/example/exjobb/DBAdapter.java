package com.example.exjobb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    /*static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";*/
	static final String KEY_ROWID = "_id";
	static final String KEY_DRUGNAME = "drug_name";
	static final String KEY_TYPE = "type";
	static final String KEY_POTENCY = "potency";
	static final String KEY_SIZE = "size";
	static final String KEY_PREFERENTIALPRICE = "preferential_price";
	static final String KEY_PRESCRIPTIONONLY = "prescription_only";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    //static final String DATABASE_TABLE = "contacts";
    static final String DATABASE_TABLE = "drugs";
    static final int DATABASE_VERSION = 2;

    /*static final String DATABASE_CREATE =
        "create table contacts (_id integer primary key autoincrement, "
        + "name text not null, email text not null);";*/

    static final String DATABASE_CREATE =
    		"create table drugs (_id integer primary key autoincrement, "
    		+ "drug_name text not null, type text not null, potency text not null, size text not null, preferential_price text not null, prescription_only text not null);";
    
    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            //db.execSQL("DROP TABLE IF EXISTS contacts");
            db.execSQL("DROP TABLE IF EXISTS drugs");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    //---insert a contact into the database---
    /*public long insertContact(String name, String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteContact(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_EMAIL}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }*/
    
    public Cursor getAllDrugs() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_DRUGNAME, KEY_TYPE, KEY_POTENCY, KEY_SIZE, KEY_PREFERENTIALPRICE, KEY_PRESCRIPTIONONLY}, null, null, null, null, null);
    }
    
    public String[] getAllDrugNames() {
    	Cursor c = db.query(DATABASE_TABLE, new String[] {KEY_DRUGNAME}, null, null, KEY_DRUGNAME, null, null);
    	String[] drugs = new String[c.getCount()];
    	
    	int i = 0;
    	if (c.moveToFirst()) {
            do {
                //DisplayContact(c);
            	drugs[i] = c.getString(0);
            	i++;
            } while (c.moveToNext());
        }
    	
    	return drugs;
    }
    
    public String[] getAllTypes(String drugName) {
    	Cursor c = db.query(DATABASE_TABLE, new String[] {KEY_TYPE}, KEY_DRUGNAME + "=?", new String[] {drugName}, KEY_TYPE, null, null);
    	String[] types = new String[c.getCount()];
    	
    	int i = 0;
    	if (c.moveToFirst()) {
            do {
                //DisplayContact(c);
            	types[i] = c.getString(0);
            	i++;
            } while (c.moveToNext());
        }
    	
    	return types;
    }
    
    public String[] getAllPotencys(String drugName, String type) {
    	Cursor c = db.query(DATABASE_TABLE, new String[] {KEY_POTENCY}, KEY_DRUGNAME + "=?" + KEY_TYPE + "=?", new String[] {drugName, type}, KEY_POTENCY, null, null);
    	String[] potency = new String[c.getCount()];
    	
    	int i = 0;
    	if (c.moveToFirst()) {
            do {
                //DisplayContact(c);
            	potency[i] = c.getString(0);
            	i++;
            } while (c.moveToNext());
        }
    	
    	return potency;
    }
    
    public String[] getAllSizes(String drugName, String type, String potency) {
    	Cursor c = db.query(DATABASE_TABLE, new String[] {KEY_SIZE}, KEY_DRUGNAME + "=?" + KEY_TYPE + "=?" + KEY_POTENCY + "=?", new String[] {drugName, type, potency}, KEY_SIZE, null, null);
    	String[] size = new String[c.getCount()];
    	
    	int i = 0;
    	if (c.moveToFirst()) {
            do {
                //DisplayContact(c);
            	size[i] = c.getString(0);
            	i++;
            } while (c.moveToNext());
        }
    	
    	return size;
    }

    //---retrieves a particular contact---
    public Cursor getDrug(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_DRUGNAME, KEY_TYPE, KEY_POTENCY, KEY_SIZE, KEY_PREFERENTIALPRICE, KEY_PRESCRIPTIONONLY}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    /*public boolean updateContact(long rowId, String name, String email) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }*/

}
