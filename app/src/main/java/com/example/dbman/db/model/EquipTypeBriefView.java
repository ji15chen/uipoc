package com.example.dbman.db.model;

/**
 * Created by jerry on 2016/11/7.
 */

import com.example.dbman.db.core.MyUUID;
import com.example.dbman.db.genupdate.daoimpl.EquipTypeDaoImpl;
import com.example.dbman.db.genupdate.schema.SysParameter;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "EquipType", daoClass =EquipTypeDaoImpl.class)
public class EquipTypeBriefView {
    @DatabaseField( columnName ="PkTypeID",id=true ,canBeNull = false, persisterClass = MyUUID.class )
    private UUID pktypeid;
    @DatabaseField( columnName ="Unit", foreign = true, foreignColumnName = "ParaID")
    private SysParameter unit;
    @DatabaseField( columnName ="CategoryID")
    private UUID categoryid;
    @DatabaseField( columnName ="WarrantyPeriod")
    private int warrantyperiod;//保修时间
    @DatabaseField( columnName ="LimitedYear")
    private int limitedyear;//
}
