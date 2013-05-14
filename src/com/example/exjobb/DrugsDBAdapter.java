package com.example.exjobb;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.exjobb.DBAdapter.DatabaseHelper;

public class DrugsDBAdapter {
	public static final String ROW_ID = "_id";
	public static final String DRUG_NAME = "drug_name";
	public static final String TYPE = "type";
	public static final String POTENCY = "potency";
	public static final String SIZE = "size";
	public static final String PREFERENTIAL_PRICE = "preferential_price";
	public static final String PRESCRIPTION_ONLY = "prescription_only";
	
	private static final String DATABASE_TABLE = "drugs";
	
	private DatabaseHelper mDBHelper;
	private SQLiteDatabase mDB;
	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DBAdapter.DATABASE_NAME, null, DBAdapter.DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public DrugsDBAdapter(Context ctx) {
		this.mCtx = ctx;
	}
	
	public DrugsDBAdapter open() throws SQLException {
		this.mDBHelper = new DatabaseHelper(this.mCtx);
		this.mDB = this.mDBHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		this.mDBHelper.close();
	}
	
	public Cursor getAllDrugs() {
		return this.mDB.query(DATABASE_TABLE, new String[] {ROW_ID, DRUG_NAME, TYPE, POTENCY, SIZE, PREFERENTIAL_PRICE, PRESCRIPTION_ONLY}, null, null, null, null, null);
	}
}
