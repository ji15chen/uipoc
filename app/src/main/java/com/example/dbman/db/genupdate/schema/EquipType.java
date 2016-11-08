package com.example.dbman.db.genupdate.schema;

	/**
	*	*@author jichen
	*/

import com.example.dbman.db.core.MyUUID;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.Date;
import com.example.dbman.db.genupdate.daoimpl.EquipTypeDaoImpl;
@DatabaseTable(tableName = "EquipType", daoClass =EquipTypeDaoImpl.class)
	public class EquipType{
@DatabaseField( columnName ="PkTypeID",id=true ,canBeNull = false)
	private UUID pktypeid;
@DatabaseField( columnName ="SupPkTypeID")
	private UUID suppktypeid;
@DatabaseField( columnName ="DeptID")
	private UUID deptid;
@DatabaseField( columnName ="UserID")
	private UUID userid;
@DatabaseField( columnName ="AddDate")
	private String adddate;
@DatabaseField( columnName ="TypeName")
	private String typename;
@DatabaseField( columnName ="TypeCode")
	private String typecode;
@DatabaseField( columnName ="Unit", foreign = true)
	private SysParameter unit;
@DatabaseField( columnName ="Scale")
	private BigDecimal scale;
@DatabaseField( columnName ="IsEnable")
	private boolean isenable;
@DatabaseField( columnName ="Describe")
	private String describe;
@DatabaseField( columnName ="CategoryID")
	private UUID categoryid;
@DatabaseField( columnName ="WarrantyPeriod")
	private int warrantyperiod;
@DatabaseField( columnName ="LimitedYear")
	private int limitedyear;

	public UUID getPkTypeID(){
		return this.pktypeid;
	}
	public void setPkTypeID(UUID PkTypeID){
		this.pktypeid=PkTypeID;
	}
	public UUID getSupPkTypeID(){
		return this.suppktypeid;
	}
	public void setSupPkTypeID(UUID SupPkTypeID){
		this.suppktypeid=SupPkTypeID;
	}
	public UUID getDeptID(){
		return this.deptid;
	}
	public void setDeptID(UUID DeptID){
		this.deptid=DeptID;
	}
	public UUID getUserID(){
		return this.userid;
	}
	public void setUserID(UUID UserID){
		this.userid=UserID;
	}
	public String getAddDate(){
		return this.adddate;
	}
	public void setAddDate(String AddDate){
		this.adddate=AddDate;
	}
	public String getTypeName(){
		return this.typename;
	}
	public void setTypeName(String TypeName){
		this.typename=TypeName;
	}
	public String getTypeCode(){
		return this.typecode;
	}
	public void setTypeCode(String TypeCode){
		this.typecode=TypeCode;
	}
	public SysParameter getUnit(){
		return this.unit;
	}
	public void setUnit(SysParameter Unit){
		this.unit=Unit;
	}
	public BigDecimal getScale(){
		return this.scale;
	}
	public void setScale(BigDecimal Scale){
		this.scale=Scale;
	}
	public boolean IsEnable(){
		return this.isenable;
	}
	public void IsEnable(boolean IsEnable){
		this.isenable=IsEnable;
	}
	public String getDescribe(){
		return this.describe;
	}
	public void setDescribe(String Describe){
		this.describe=Describe;
	}
	public UUID getCategoryID(){
		return this.categoryid;
	}
	public void setCategoryID(UUID CategoryID){
		this.categoryid=CategoryID;
	}
	public int getWarrantyPeriod(){
		return this.warrantyperiod;
	}
	public void setWarrantyPeriod(int WarrantyPeriod){
		this.warrantyperiod=WarrantyPeriod;
	}
	public int getLimitedYear(){
		return this.limitedyear;
	}
	public void setLimitedYear(int LimitedYear){
		this.limitedyear=LimitedYear;
	}

}