package com.example.exjobb;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	public static final String DATABASE_NAME = "mydb";
	public static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_TABLE_DRUGS = "create table drugs (_id integer primary key autoincrement, "
			+ DrugsDBAdapter.DRUG_NAME + " TEXT,"
			+ DrugsDBAdapter.TYPE + " TEXT,"
			+ DrugsDBAdapter.POTENCY + " TEXT,"
			+ DrugsDBAdapter.SIZE + " TEXT,"
			+ DrugsDBAdapter.PREFERENTIAL_PRICE + " TEXT,"
			+ DrugsDBAdapter.PRESCRIPTION_ONLY + " TEXT" + ");";
	
	/**private static final String CREATE_TABLE_PHARMACIES = "create table pharmacies (_id integer primary key autoincrement, "
			+ PharmaciesDBAdapter.CHAIN_NAME + " TEXT,"
			+ PharmaciesDBAdapter.PHARMACY_NAME + " TEXT,"
			+ PharmaciesDBAdapter.ADDRESS + " TEXT,"
			+ PharmaciesDBAdapter.POSTAL_CODE + " INTEGER,"
			+ PharmaciesDBAdapter.POSTAL_AREA + " TEXT,"
			+ PharmaciesDBAdapter.LATITUDE + " TEXT,"
			+ PharmaciesDBAdapter.LONGITUDE + " TEXT,"
			+ PharmaciesDBAdapter.PHONE_NBR + " TEXT,"
			+ PharmaciesDBAdapter.OPENING_HOURS_WD + " TEXT,"
			+ PharmaciesDBAdapter.CLOSING_HOURS_WD + " TEXT,"
			+ PharmaciesDBAdapter.OPENING_HOURS_SAT + " TEXT,"
			+ PharmaciesDBAdapter.CLOSING_HOURS_SAT + " TEXT,"
			+ PharmaciesDBAdapter.OPENING_HOURS_SUN + " TEXT,"
			+ PharmaciesDBAdapter.CLOSING_HOURS_SUN + " TEXT" + ");";
	
	private static final String CREATE_TABLE_STOCK = "create table stock "
			+ StockDBAdapter.DRUG_ID + "(INTEGER,"
			+ StockDBAdapter.PHARMACY_ID + " INTEGER,"
			+ StockDBAdapter.NUMBER + " INTEGER,"
			+ StockDBAdapter.PRICE + " INTEGER" + ");";**/
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		this.DBHelper = new DatabaseHelper(this.context);
	}
	
	public static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_DRUGS);
			//db.execSQL(CREATE_TABLE_PHARMACIES);
			//db.execSQL(CREATE_TABLE_STOCK);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		}
		
	}
	
	public DBAdapter open() throws SQLException {
		this.db = this.DBHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		this.DBHelper.close();
	}
		
}
