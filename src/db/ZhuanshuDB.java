   package db;

   /*
    * 数据库套用郭霖大神的coolweather
   */

	import java.util.ArrayList;
	import java.util.List;

	import model.City;
	import model.County;
	import model.Province;
	import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
	import java.util.ArrayList;
	import java.util.List;


	/*
	 * 单例类
	 */
	public class ZhuanshuDB {
		/*
		 * 定义数据库名
		 */
		public final static String DB_NAME = "cool_weather";
		/*
		 * 数据库版本
		 */
		public final static int VERSION = 1;
		private static ZhuanshuDB zhuanshuDB;
		private SQLiteDatabase db;

		/*
		 * 将构造方法私有化
		 */
		private ZhuanshuDB(Context context) {
			dbhelper dbHelper = new dbhelper(context,
					DB_NAME, null, VERSION);
			db = dbHelper.getWritableDatabase();
		}

		/*
		 * 获取ZhuanshuDB实例 synchronized定一个方法或者一个代码块的时候，同一时刻最多只有一个线程执行这段代码
		 */
		public synchronized static ZhuanshuDB getInstance(Context context) {
			if (zhuanshuDB == null) {
				zhuanshuDB = new ZhuanshuDB(context);
			}
			return zhuanshuDB;
		}

		/**
		 * 将province实例存储到数据库,我们在数据库中设置id为increment这里不需要对id赋值
		 */
		public void saveProvince(Province province) {
			if (province != null) {
				ContentValues values = new ContentValues();
				values.put("province_name", province.getProvinceName());
				values.put("province_code", province.getProvinceCode());
				db.insert("Province", null, values);

			}
		}

		/*
		 * 从数据库中读取全国所有的省份信息
		 */
		public List<Province> loadProvinces() {
			List<Province> list = new ArrayList<Province>();
			Cursor cursor = db
					.query("Province", null, null, null, null, null, null);
			if (cursor.moveToFirst()) {
				do {
					Province province = new Province();
					province.setId(cursor.getInt(cursor.getColumnIndex("id")));
					province.setProvinceName(cursor.getString(cursor
							.getColumnIndex("province_name")));
					province.setProvinceCode(cursor.getString(cursor
							.getColumnIndex("province_code")));
					list.add(province);
				} while (cursor.moveToNext());
			}
			if (cursor != null) {
				cursor.close();
			}
			return list;

		}

		/**
		 * 将city实例存储到数据库
		 */
		public void saveCity(City city) {
			if (city != null) {
				ContentValues values = new ContentValues();
				values.put("city_name", city.getCityName());
				values.put("city_code", city.getCityCode());
				values.put("province_id", city.getProvinceId());
				db.insert("City", null, values);
			}

		}

		/*
		 * 从数据库中读取全国所有的城市信息
		 */
		public List<City> loadCities(int provinceId) {
			List<City> list = new ArrayList<City>();
			Cursor cursor = db.query("City", null, "province_id=?",
					new String[] { String.valueOf(provinceId) }, null, null, null);
			if (cursor.moveToFirst()) {
				do {
					City city = new City();
					city.setId(cursor.getInt(cursor.getColumnIndex("id")));
					city.setCityName(cursor.getString(cursor
							.getColumnIndex("city_name")));
					city.setCityCode(cursor.getString(cursor
							.getColumnIndex("city_code")));
					city.setProvinceId(provinceId);
					list.add(city);
				} while (cursor.moveToNext());
			}
			if (cursor != null) {
				cursor.close();
			}
			return list;
		}

		/**
		 * 将county实例存储到数据库
		 */
		public void saveCounty(County county) {
			if (county != null) {
				ContentValues values = new ContentValues();
				values.put("county_name", county.getCountyName());
				values.put("county_code", county.getCountyCode());
				values.put("city_id", county.getCityId());
				db.insert("County", null, values);
			}
		}

		/*
		 * 从数据库中读取全国所有的县信息
		 */
		public List<County> loadCounties(int cityId) {
			List<County> list = new ArrayList<County>();
			Cursor cursor = db.query("County", null, "city_id=?",
					new String[] { String.valueOf(cityId) }, null, null, null);
			if (cursor.moveToFirst()) {
				do {
					County county = new County();
					county.setId(cursor.getInt(cursor.getColumnIndex("id")));
					county.setCountyName(cursor.getString(cursor
							.getColumnIndex("county_name")));
					county.setCountyCode(cursor.getString(cursor
							.getColumnIndex("county_code")));
					county.setCityId(cityId);
					list.add(county);
				} while (cursor.moveToNext());

			}
			if (cursor != null) {
				cursor.close();
			}
			return list;

		}
	}


