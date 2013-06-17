package com.example.exjobb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
	
	static final String KEY_DNAME = "drug_name";
	static final String KEY_TYPE = "type";
	static final String KEY_POTENCY = "potency";
	static final String KEY_SIZE = "size";
	static final String KEY_PREFERENTIALPRICE = "preferential_price";
	static final String KEY_PRESCRIPTIONONLY = "prescription_only";
	
	static final String KEY_CHNAME = "chain_name";
	static final String KEY_PHNAME = "pharmacy_name";
	static final String KEY_ADDRESS = "address";
	static final String KEY_PCODE = "postal_code";
	static final String KEY_PAREA = "postal_area";
	static final String KEY_OPHWD = "opening_hours_wd";
	static final String KEY_CLHWD = "closing_hours_wd";
	static final String KEY_OPHSAT = "opening_hours_sat";
	static final String KEY_CLHSAT = "closing_hours_sat";
	static final String KEY_OPHSUN = "opening_hours_sun";
	static final String KEY_CLHSUN = "closing_hours_sun";
	static final String KEY_LAT = "latitude";
	static final String KEY_LON = "longitude";
	
	static final String KEY_DID = "drug_id";
	static final String KEY_PID = "pharmacy_id";
	static final String KEY_NBR = "number";
	static final String KEY_PRICE = "price";
	
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    //static final String DATABASE_TABLE = "contacts";
    static final String DATABASE_TABLE_DR = "drugs";
    static final String DATABASE_TABLE_PH = "pharmacies"; 
    static final String DATABASE_TABLE_ST = "stock";
    static final int DATABASE_VERSION = 2;

    /*static final String DATABASE_CREATE =
        "create table contacts (_id integer primary key autoincrement, "
        + "name text not null, email text not null);";*/

    static final String DATABASE_CREATE_DR =
    		"create table drugs (_id integer primary key autoincrement, "
    		+ "drug_name text not null, type text not null, potency text not null, size text not null, preferential_price text not null, prescription_only text not null);";
    
    static final String DATABASE_CREATE_PH =
    		"create table pharmacies (_id integer primary key autoincrement, "
    		+ "chain_name text not null, pharmacy_name text not null, address text not null, postal_code text not null, postal_area text not null, opening_hours_wd text not null, closing_hours_wd not null, opening_hours_sat text not null, closing_hours_sat text not null, opening_hours_sun text not null, closing_hour_sun text not null, latitude text not null, longtitude text not null);";
    
    static final String DATABASE_CREATE_ST = 
    		"create table stock (drug_id integer not null, pharmacy_id integer not null, number text not null, price text not null, FOREGIN KEY(drug_id) REFERENCES drugs(_id), FOREGIN KEY(pharmacy_id) REFERENCES pharmacies(_id));";
    
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
                db.execSQL(DATABASE_CREATE_DR);
                db.execSQL(DATABASE_CREATE_PH);
                db.execSQL(DATABASE_CREATE_ST);
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
            db.execSQL("DROP TABLE IF EXISTS pharmacies");
            db.execSQL("DROP TABLE IF EXISTS stock");
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
        return db.query(DATABASE_TABLE_DR, new String[] {KEY_ROWID, KEY_DNAME, KEY_TYPE, KEY_POTENCY, KEY_SIZE, KEY_PREFERENTIALPRICE, KEY_PRESCRIPTIONONLY}, null, null, null, null, null);
    }
    
    public ArrayList<String> getAllDrugNames() {
    	Cursor c = db.query(DATABASE_TABLE_DR, new String[] {KEY_DNAME}, null, null, KEY_DNAME, null, null);
    	ArrayList<String> drugs = new ArrayList<String>();
    	
    	int i = 0;
    	if (c.moveToFirst()) {
            do {
            	drugs.add(i, c.getString(0));
            	i++;
            } while (c.moveToNext());
        }
    	
    	c.close();
    	
    	return drugs;
    }
    
    public ArrayList<String> getAllTypes(String drugName) {
    	Cursor c = db.query(DATABASE_TABLE_DR, new String[] {KEY_TYPE}, KEY_DNAME + "=?", new String[] {drugName}, KEY_TYPE, null, null);
    	ArrayList<String> types = new ArrayList<String>();
    	
    	int i = 0;
    	if (c.moveToFirst()) {
            do {
            	types.add(i, c.getString(0));
            	i++;
            } while (c.moveToNext());
        }
    	
    	c.close();
    	
    	return types;
    }
    
    public ArrayList<String> getAllStrengths(String drugName, String type) {
    	Cursor c = db.query(DATABASE_TABLE_DR, new String[] {KEY_POTENCY}, KEY_DNAME + "=? and " + KEY_TYPE + "=?", new String[] {drugName, type}, KEY_POTENCY, null, null);
    	ArrayList<String> strengths = new ArrayList<String>();
    	//String[] potency = new String[c.getCount()];
    	
    	int i = 0;
    	if (c.moveToFirst()) {
            do {
                //DisplayContact(c);
            	strengths.add(i, c.getString(0));
            	i++;
            } while (c.moveToNext());
        }
    	
    	c.close();
    	
    	return strengths;
    }
    
    public ArrayList<String> getAllSizes(String drugName, String type, String potency) {
    	Cursor c = db.query(DATABASE_TABLE_DR, new String[] {KEY_SIZE}, KEY_DNAME + "=? and " + KEY_TYPE + "=? and " + KEY_POTENCY + "=?", new String[] {drugName, type, potency}, KEY_SIZE, null, null);
    	ArrayList<String> sizes = new ArrayList<String>();
    	//String[] size = new String[c.getCount()];
    	
    	int i = 0;
    	if (c.moveToFirst()) {
            do {
                //DisplayContact(c);
            	sizes.add(c.getString(0));
            	i++;
            } while (c.moveToNext());
        }
    	
    	return sizes;
    }
    
    public ArrayList<Pharmacy> getAllPharmaciesWithDrugId(String dID) {
    	/*Calendar cal = Calendar.getInstance();
    	int day = cal.get(Calendar.DAY_OF_WEEK);
    	
    	if(day == 1) { //Sunday
    		Cursor c = db.query(DATABASE_TABLE_PH, new String[] {KEY_CHNAME, KEY_PHNAME, KEY_LAT, KEY_LON}, KEY_DNAME + "=? and " + KEY_TYPE + "=? and " + KEY_POTENCY + "=? and " + KEY_SIZE + "=?", new String[] {drugName, type, potency, size}, null, null, null);
    	} else if(day == 7) { //Saturday
    		Cursor c = db.query(DATABASE_TABLE_PH, new String[] {KEY_CHNAME, KEY_PHNAME, KEY_LAT, KEY_LON}, KEY_DNAME + "=? and " + KEY_TYPE + "=? and " + KEY_POTENCY + "=? and " + KEY_SIZE + "=?", new String[] {drugName, type, potency, size}, null, null, null);
    	} else { //Weekday
    		Cursor c = db.query(DATABASE_TABLE_PH, new String[] {KEY_CHNAME, KEY_PHNAME, KEY_LAT, KEY_LON}, KEY_DNAME + "=? and " + KEY_TYPE + "=? and " + KEY_POTENCY + "=? and " + KEY_SIZE + "=?", new String[] {drugName, type, potency, size}, null, null, null);
    	}*/
    	
    	ArrayList<String> pIDs = getAllPharmacyIdWithDrugId(dID);
    	StringBuffer queryIN = new StringBuffer(" in(");
    	for(int i = 0; i < pIDs.size()-1; i++) {
    		queryIN.append(pIDs.get(i) + ",");
    	}
    	
    	queryIN.append(pIDs.get(pIDs.size()-1));
    	queryIN.append(")");
    	
    	//Cursor c = db.query(DATABASE_TABLE_PH, new String[] {KEY_CHNAME, KEY_PHNAME}, KEY_ROWID + "IN(?,?)", new String[] {"2", "5"}, null, null, null);
    	//Cursor c = db.rawQuery("select * from " + DATABASE_TABLE_PH + " where " + KEY_ROWID + " in(?,?)", new String[] {"2", "5"});
    	//Cursor c = db.rawQuery("select * from " + DATABASE_TABLE_PH + " where " + KEY_ROWID + " in(2,5)", null);
    	Cursor c = db.rawQuery("select * from " + DATABASE_TABLE_PH + " where " + KEY_ROWID + " " + queryIN, null);
    	ArrayList<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
    	
    	int i = 0;
    	if (c.moveToFirst()) {
            do {
                //DisplayContact(c);
            	Pharmacy ph = new Pharmacy(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10), c.getString(11), c.getString(12), c.getString(13), c.getString(14));
            	pharmacies.add(ph);
            	i++;
            } while (c.moveToNext());
        }
    	
    	return pharmacies;
    }
    
    public ArrayList<String> getAllPharmacyIdWithDrugId (String dID) {
    	Cursor c = db.query(DATABASE_TABLE_ST, new String[] {KEY_PID}, KEY_DID + "=?", new String[] {dID}, null, null, null);
    	//String[] phids = new String[c.getCount()];
    	ArrayList<String> phids = new ArrayList<String>();
    	
    	int i = 0;
    	if (c.moveToFirst()) {
            do {
            	phids.add(c.getString(0));
            	i++;
            } while (c.moveToNext());
        }
    	
    	return phids;
    }

    public String getDrugRowId(String drugName, String type, String potency, String size) {
    	Cursor c = db.query(DATABASE_TABLE_DR, new String[] {KEY_ROWID}, KEY_DNAME + "=? and " + KEY_TYPE + "=? and " + KEY_POTENCY + "=? and " + KEY_SIZE + "=?", new String[] {drugName, type, potency, size}, null, null, null);
    	String id = null;
    	
    	if(c.moveToFirst()) {
    		do {
    			id = c.getString(0);
    		} while(c.moveToNext());
    	}
    	
    	return id;
    }
    
    /*//---retrieves a particular contact---
    public Cursor getDrug(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_DR, new String[] {KEY_ROWID, KEY_DNAME, KEY_TYPE, KEY_POTENCY, KEY_SIZE, KEY_PREFERENTIALPRICE, KEY_PRESCRIPTIONONLY}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }*/
    
    //---updates a contact---
    /*public boolean updateContact(long rowId, String name, String email) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }*/

}
