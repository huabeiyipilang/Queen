package cn.kli.queen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private final static String DB_NAME = "lottery";
	private static final int DATABASE_VERSION = 1;
	
	final static String TABLE_LOG = "log";
	final static String LOG_ID = "_id";
	final static String LOG_PRIZE = "prize";
	final static String LOG_ROLL = "roll";
	final static String LOG_TIME = "time";
	
	final static String TABLE_INFO = "info";
	final static String INFO_TOTAL = "total";
	
	public final static String TABLE_WISH = "wish";
	public final static String WISH_ID = "_id";
	public final static String WISH_CONTENT = "content";
	public final static String WISH_TIME = "time";
	public final static String WISH_ACHIEVE_TIME = "achieve_time";
	public final static String WISH_ACHIEVE = "achieve";
	
	final static String[] QUERY_COLUM_LOTTERY_HISTORY = {
		LOG_PRIZE, LOG_ROLL, LOG_TIME, LOG_ID, 
	};
	
	final static String[] QUERY_COLUM_WISH = {
		WISH_CONTENT, WISH_TIME, WISH_ACHIEVE_TIME, WISH_ACHIEVE,WISH_ID
	};
	
	private static DbHelper sSingleton = null;
	
	public static DbHelper getInstance(Context context){
		Log.i("klilog","DbHelper getInstance");
		if(sSingleton == null){
			sSingleton = new DbHelper(context, DB_NAME, null, DATABASE_VERSION);
		}
		return sSingleton;
	}

	private DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("klilog","DbHelper onCreate()");
		final String create_table_log = "CREATE TABLE "+ TABLE_LOG +" ("
				+LOG_ID +" INTEGER primary key, "
				+LOG_PRIZE+" INTEGER, "
				+LOG_ROLL+" INTEGER, "
				+LOG_TIME+" timestamp)";
		db.execSQL(create_table_log);
		
		db.execSQL("CREATE TABLE " + TABLE_INFO+"("
				+INFO_TOTAL + "int)");
		
		final String create_table_wish = "CREATE TABLE "+TABLE_WISH+"("
				+WISH_ID+" INTEGER primary key, "
				+WISH_CONTENT+" TEXT, "
				+WISH_TIME+" timestamp, "
				+WISH_ACHIEVE_TIME+" timestamp, "
				+WISH_ACHIEVE+ " INTEGER)";
		db.execSQL(create_table_wish);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public Cursor getLottryHistoryCursor(){
		Cursor cursor = getReadableDatabase().query(TABLE_LOG, QUERY_COLUM_LOTTERY_HISTORY, 
				null, null, null, null, LOG_TIME+" desc");
		return cursor;
	}
	
	public Cursor getWishCursor(){
		return getReadableDatabase().query(TABLE_WISH, QUERY_COLUM_WISH, 
				null, null, null, null, WISH_TIME+" desc");
	}

}
