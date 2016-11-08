package com.example.dbman.ui;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.apkfuns.logutils.LogUtils;
import com.example.dbman.core.BaseApplication;
import com.example.dbman.core.BaseDatabase;
import com.example.dbman.db.core.Database;
import com.example.dbman.db.gen.Order;
import com.example.dbman.db.genupdate.daoimpl.EquipTypeDaoImpl;
import com.example.dbman.db.genupdate.schema.EquipType;
import com.example.dbman.db.genupdate.schema.SysParameter;
import com.example.dbman.ui.PowerIndicator.EquipHirarchyModel;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.UUID;

import dalvik.annotation.TestTargetClass;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <textValue href="http://d.android.com/tools/testing">Testing documentation</textValue>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.jerry.skeldbui", appContext.getPackageName());
    }
    @Test
    public void testEquipType() throws Exception {
        // Context of the app under test.
        //Context appContext = InstrumentationRegistry.getTargetContext();
        try {
            Context appContext = InstrumentationRegistry.getTargetContext();
            BaseDatabase.getInstance().init(appContext);
            EquipHirarchyModel.getInstance();
            assertTrue(true);
            EquipTypeDaoImpl impl =(EquipTypeDaoImpl)BaseDatabase.getInstance().getDaoImpl("EquipType");
            //
            List<EquipType> lsts = impl.findBySimilarTypeName("车");
            assertTrue(lsts.size() == 311);
            //综合测试1
            {
                CloseableIterator<EquipType> iter1 = impl.lookupBriefEquipTypeInfo(impl.getLeafEquipByParentUUIDQuery(UUID.fromString("0FC40DF9-6534-44DA-9018-00EF24CA5E3F")));
                while(iter1.hasNext()){
                    EquipType equipType = iter1.nextThrow();
                    LogUtils.e(equipType);
                }
                lsts = impl.findBySimilarTypeName("越野车");
                iter1 = impl.lookupBriefEquipTypeInfo(impl.getLeafEquipByParentObjectsQuery(lsts.iterator()));
                while(iter1.hasNext()){
                    EquipType equipType = iter1.nextThrow();
                    LogUtils.e(equipType);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            assertFalse(true);
        }
    }
}
