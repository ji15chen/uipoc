package com.example.dbman.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.example.dbman.db.genupdate.daoimpl.*;
import com.example.dbman.db.genupdate.schema.*;
import com.example.dbman.ui.R;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.HashMap;

/**
 * Created by ChenJi on 2016/10/26.
 */

public class BaseDatabase {
    private static final String DB_NAME="dbman.db";
    private static BaseDatabase baseDatabase = new BaseDatabase();
    private static String DATABASE_PATH;
    private static String DATABASE_URL;
    private static String DATABASE_DIR;
    private JdbcConnectionSource connectionSource;

    public enum TableNames {
    CheckMntcDetail,
    CheckMntcInfo,
    CpntTypes,
    Department,
    DepotInfo,
    DeptExent,
    ECFactory,
    EqmtCpntInfo,
    EqmtInOut,
    EquipLog,
    EquipType,
    EquipTypeDetail,
    ExentData,
    ExtendType,
    FactoryInfo,
    FaultInfor,
    PersonInfo,
    PersonInOut,
    RoleKey,
    RoleMenu,
    RoomAdminUser,
    ScanStore,
    ScanStoreDetail,
    ScanStoreEquipType,
    StoreDetail,
    StoreExtendChange,
    Sysdiagrams,
    SysFileInfo,
    SysKey,
    SysLog,
    SysMenu,
    SysParameter,
    SysRole,
    SysUser,
    TmpHmdData,
    UserCard,
    UserDataPermission,
    Workers
    };

    private static final Class schemaClasses [] ={
            CheckMntcDetail.class,
            CheckMntcInfo.class,
            CpntTypes.class,
            Department.class,
            DepotInfo.class,
            DeptExent.class,
            ECFactory.class,
            EqmtCpntInfo.class,
            EqmtInOut.class,
            EquipLog.class,
            EquipType.class,
            EquipTypeDetail.class,
            ExentData.class,
            ExtendType.class,
            FactoryInfo.class,
            FaultInfor.class,
            PersonInfo.class,
            PersonInOut.class,
            RoleKey.class,
            RoleMenu.class,
            RoomAdminUser.class,
            ScanStore.class,
            ScanStoreDetail.class,
            ScanStoreEquipType.class,
            StoreDetail.class,
            StoreExtendChange.class,
            Sysdiagrams.class,
            SysFileInfo.class,
            SysKey.class,
            SysLog.class,
            SysMenu.class,
            SysParameter.class,
            SysRole.class,
            SysUser.class,
            TmpHmdData.class,
            UserCard.class,
            UserDataPermission.class,
            Workers.class
    };

    private static final Class [] daoImplClasses = {
            CheckMntcDetailDaoImpl.class,
            CheckMntcInfoDaoImpl.class,
            CpntTypesDaoImpl.class,
            DepartmentDaoImpl.class,
            DepotInfoDaoImpl.class,
            DeptExentDaoImpl.class,
            ECFactoryDaoImpl.class,
            EqmtCpntInfoDaoImpl.class,
            EqmtInOutDaoImpl.class,
            EquipLogDaoImpl.class,
            EquipTypeDaoImpl.class,
            EquipTypeDetailDaoImpl.class,
            ExentDataDaoImpl.class,
            ExtendTypeDaoImpl.class,
            FactoryInfoDaoImpl.class,
            FaultInforDaoImpl.class,
            PersonInfoDaoImpl.class,
            PersonInOutDaoImpl.class,
            RoleKeyDaoImpl.class,
            RoleMenuDaoImpl.class,
            RoomAdminUserDaoImpl.class,
            ScanStoreDaoImpl.class,
            ScanStoreDetailDaoImpl.class,
            ScanStoreEquipTypeDaoImpl.class,
            StoreDetailDaoImpl.class,
            StoreExtendChangeDaoImpl.class,
            SysdiagramsDaoImpl.class,
            SysFileInfoDaoImpl.class,
            SysKeyDaoImpl.class,
            SysLogDaoImpl.class,
            SysMenuDaoImpl.class,
            SysParameterDaoImpl.class,
            SysRoleDaoImpl.class,
            SysUserDaoImpl.class,
            TmpHmdDataDaoImpl.class,
            UserCardDaoImpl.class,
            UserDataPermissionDaoImpl.class,
            WorkersDaoImpl.class
    };

    public JdbcConnectionSource getConnectionSource() {
        return connectionSource;
    }

    private HashMap<String,BaseDaoImpl> daoHashMap = new HashMap<String, BaseDaoImpl>();

    private void initDao(ConnectionSource connectionSources){
        for (Class clszz: daoImplClasses){
            try {
                Constructor constructor = clszz.getConstructor(ConnectionSource.class);
                {
                    String name = clszz.getSimpleName();
                    int inx = name.indexOf("DaoImpl");
                    if (inx >= 0){
                        name = name.substring(0,inx);
                    }
                    BaseDaoImpl impl = (BaseDaoImpl) constructor.newInstance(connectionSources);
                    daoHashMap.put(name, impl);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static BaseDatabase getInstance() {
        return baseDatabase;
    }

    private SQLiteDatabase db;

    private void createDBIfNotExist(){
             File dir = new File(DATABASE_DIR);
            if (!dir.exists()) {
                dir.mkdir();
            }//如果该目录不存在，创建该目录

            File filepath = new File(DATABASE_PATH);
            if ((Constants.debug) || ( !filepath.exists()) ) {//如果文件不存在
                try {
                    InputStream inputStream = BaseApplication.getApp().getResources().openRawResource(R.raw.dbman);//将raw中的test.db放入输入流中
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

    public boolean init(Context app){
        try {
            DATABASE_DIR = "/data/data/" + app.getPackageName()+"/databases";
            DATABASE_PATH = DATABASE_DIR + "/" + DB_NAME;
            DATABASE_URL = "jdbc:sqlite:/"+ DATABASE_PATH;
            try {
                DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
                createDBIfNotExist();
                connectionSource = new JdbcConnectionSource(DATABASE_URL);
                initDao(connectionSource);
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            LogUtils.e("ui database error",e);
            return false;
        }
        return true;
    }

    public synchronized BaseDaoImpl getDaoImpl(String tableName){
        return daoHashMap.get(tableName);
    }

    public synchronized Connection getConnection(){
        try {
            JdbcDatabaseConnection jdbcDatabaseConnection = (JdbcDatabaseConnection)connectionSource.getReadWriteConnection("");
            return jdbcDatabaseConnection.getInternalConnection();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
