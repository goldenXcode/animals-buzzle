package ferus.tigris.buzzle.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DB extends SQLiteOpenHelper {

	private static final String T_LEVELS = "levels";
	private static final String T_LOVES = "loves";
	private static final String T_PERSONAGES = "personages";
	private static final String T_RESULTS = "results";

	public DB(Context context) {
		super(context, "mydb", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + T_RESULTS + " ("
				+"id integer primary key autoincrement,"
				+"level_id integer,"
				+"result integer"
				+");"
				);
		db.execSQL("create table " + T_LOVES + " ("
				+"id integer primary key autoincrement,"
				+"level_id integer,"
				+"loves integer"
				+");"
				);
		db.execSQL("create table " + T_LEVELS + " ("
				+"id integer primary key autoincrement,"
				+"level text,"
				+"scope integer,"
				+"complexity integer"
				+");"
				);
		db.execSQL("create table " + T_PERSONAGES + " ("
				+"id integer primary key autoincrement,"
				+"level_id integer,"
				+"personage text,"
				+"x integer,"
				+"y integer"
				+");"
				);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + T_RESULTS + ";");
		db.execSQL("DROP TABLE IF EXISTS " + T_LEVELS + ";");
		db.execSQL("DROP TABLE IF EXISTS " + T_PERSONAGES + ";");
		onCreate(db);
	}

	public String loadLevel(long level_id) {
		SQLiteDatabase db = getWritableDatabase();
		String[] args = new String[] {String.valueOf(level_id)};
		
		Cursor c = db.query(T_LEVELS, null, "id = ?", args, null, null, null, null);
		if(c.moveToFirst()) {
			do {
				String name = c.getString(c.getColumnIndex("level"));
				Log.d("ferus.tigris.db", name);
				return name;
			} while(c.moveToNext());
		}
		return "";
	}

	public boolean isSave() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(T_LEVELS, null, null, null, null, null, null, null);
		return c.getCount() > 0;
	}

	public long saveLevel(String levelType, int scope, int complexity) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(T_LEVELS, null, null);

		ContentValues values = new ContentValues();
		values.put("level", levelType);
		values.put("scope", scope);
		values.put("complexity", complexity);
		return db.insert(T_LEVELS, null, values);
	}

	public long loadLastLevelId() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(T_LEVELS, null, null, null, null, null, null, null);
		if(c.moveToFirst()) {
			do {
				int res = c.getInt(c.getColumnIndex("id"));
				Log.d("ferus.tigris.db", String.valueOf(res));
				return res;
			} while(c.moveToNext());
		}
		return -1;
	}

	public List<PersonageInfo> loadPersonages(long level_id) {
		List<PersonageInfo> res = new ArrayList<PersonageInfo>();
		
		SQLiteDatabase db = getReadableDatabase();
		String[] args = new String[] {String.valueOf(level_id)};
		
		Cursor c = db.query(T_PERSONAGES, null, "level_id = ?", args, null, null, null, null);
		if(c.moveToFirst()) {
			do {
				PersonageInfo info = new PersonageInfo();
				info.name = c.getString(c.getColumnIndex("personage"));
				info.x = c.getInt(c.getColumnIndex("x"));
				info.y = c.getInt(c.getColumnIndex("y"));
				res.add(info);

				Log.d("ferus.tigris.db", info.name + String.valueOf(info.x) + String.valueOf(info.y));

			} while(c.moveToNext());
		}
		return res;
	}

	public void savePersonages(long level_id, List<PersonageInfo> personages) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(T_PERSONAGES, null, null);

		for(PersonageInfo info: personages) {
			ContentValues values = new ContentValues();
			values.put("level_id", level_id);
			values.put("personage", info.name);
			values.put("x", info.x);
			values.put("y", info.y);
			db.insert(T_PERSONAGES, null, values);
		}
	}

	public int loadLevelScope(long level_id) {
		SQLiteDatabase db = getReadableDatabase();
		String[] args = new String[] {String.valueOf(level_id)};
		Cursor c = db.query(T_LEVELS, null, "id = ?", args, null, null, null, null);
		if(c.moveToFirst()) {
			do {
				int res = c.getInt(c.getColumnIndex("scope"));
				Log.d("ferus.tigris.db", String.valueOf(res));
				return res;
			} while(c.moveToNext());
		}
		return -1;
	}

	public int loadLevelComplexity(long level_id) {
		SQLiteDatabase db = getReadableDatabase();
		String[] args = new String[] {String.valueOf(level_id)};
		Cursor c = db.query(T_LEVELS, null, "id = ?", args, null, null, null, null);
		if(c.moveToFirst()) {
			do {
				int res = c.getInt(c.getColumnIndex("complexity"));
				Log.d("ferus.tigris.db", String.valueOf(res));
				return res;
			} while(c.moveToNext());
		}
		return -1;
	}

	public int loadScope(long level_id) {
		SQLiteDatabase db = getReadableDatabase();
		String[] args = new String[] {String.valueOf(level_id)};
		Cursor c = db.query(T_RESULTS, null, "level_id = ?", args, null, null, null, null);
		if(c.moveToFirst()) {
			do {
				int res = c.getInt(c.getColumnIndex("result"));
				Log.d("ferus.tigris.db", String.valueOf(res));
				return res;
			} while(c.moveToNext());
		}
		return -1;
	}

	public void saveScope(long level_id, int scope) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("result", scope);
		values.put("level_id", level_id);
		db.insert(T_RESULTS, null, values);
	}

	public int loadLoves(long level_id) {
		SQLiteDatabase db = getReadableDatabase();
		String[] args = new String[] {String.valueOf(level_id)};
		Cursor c = db.query(T_LOVES, null, "level_id = ?", args, null, null, null, null);
		if(c.moveToFirst()) {
			do {
				int res = c.getInt(c.getColumnIndex("loves"));
				Log.d("ferus.tigris.db", String.valueOf(res));
				return res;
			} while(c.moveToNext());
		}
		return -1;
	}

	public void saveLoves(long level_id, int scope) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("loves", scope);
		values.put("level_id", level_id);
		db.insert(T_LOVES, null, values);
	}

	public ArrayList<Integer> loadTopResults() {
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(T_RESULTS, null, null, null, null, null, null, null);
		if(c.moveToFirst()) {
			do {
				results.add(c.getInt(c.getColumnIndex("result")));
			} while(c.moveToNext());
		}
		return results;
	}

}
