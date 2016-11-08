package com.example.dbman.db.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dbman.core.BaseApplication;
import com.example.dbman.ui.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
@Deprecated
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	public static final String DATABASE_NAME="dbman_bak.dbk.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	private static String DATABASE_PATH;
	private static String DATABASE_URL;
	private static String DATABASE_DIR;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		DATABASE_DIR = "/data/data/" + context.getPackageName();
		DATABASE_PATH = DATABASE_DIR + "/" + DATABASE_NAME;
		DATABASE_URL = "jdbc:sqlite:/"+ DATABASE_PATH;
	}

	private void createDBIfNotExist(){
		File dir = new File(DATABASE_DIR);
		if (!dir.exists()) {
			dir.mkdir();
		}//如果该目录不存在，创建该目录

		File filepath = new File(DATABASE_PATH);
		if (!filepath.exists()) {//如果文件不存在
			try {
				InputStream inputStream = BaseApplication.getApp().getResources().openRawResource(R.raw.dbman_bak);//将raw中的test.db放入输入流中
				FileOutputStream fileOutputStream = new FileOutputStream(DATABASE_PATH);//将新的文件放入输出流中
				byte[] buff = new byte[8192];
				int len = 0;
				while ((len = inputStream.read(buff)) > 0) {
					fileOutputStream.write(buff, 0, len);
				}
				fileOutputStream.close();
				inputStream.close();
			} catch (Exception e) {
				Log.i("info","无法复制");
				e.printStackTrace();
			}
		}//写入文件结束
		Log.i("filepath"," "+filepath);
	}
	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			createDBIfNotExist();
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			// after we drop the old databases, we create the new ones
			File filepath = new File(DATABASE_PATH);
			if (filepath.exists()){
				filepath.delete();
			}
			onCreate(db, connectionSource);
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

}
