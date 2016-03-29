package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class dbhelper extends SQLiteOpenHelper{
	

	/*
	 * ʡ�ݽ�����䣬ע�������д��ÿ�������ֶ�֮��Ӷ���
	 */
	public final static String CREATE_PROVINCE = "create table Province("
			+ "id integer primary key autoincrement," + "province_name text,"
			+ "province_code text)";
	/*
	 * ���н������
	 */
	public final static String CREATE_CITY = "create table City("
			+ "id integer primary key autoincrement," + "city_name text,"
			+ "city_code text," + "province_id integer)";
	/*
	 * �ؽ������
	 */
	public final static String CREATE_COUNTY = "create table County("
			+ "id integer primary key autoincrement," + "county_name text,"
			+ "county_code text," + "city_id integer)";

	public dbhelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PROVINCE);
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_COUNTY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}


