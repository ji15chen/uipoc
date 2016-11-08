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
import com.example.dbman.db.genupdate.daoimpl.SysParameterDaoImpl;
@DatabaseTable(tableName = "SysParameter", daoClass =SysParameterDaoImpl.class)
	public class SysParameter{
@DatabaseField( columnName ="id",id=true ,canBeNull = false )
	private UUID id;
@DatabaseField( columnName ="ParentID")
	private UUID parentid;
@DatabaseField( columnName ="ParaName")
	private String paraname;
@DatabaseField( columnName ="ParaSort")
	private int parasort;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getParentID(){
		return this.parentid;
	}
	public void setParentID(UUID ParentID){
		this.parentid=ParentID;
	}
	public String getParaName(){
		return this.paraname;
	}
	public void setParaName(String ParaName){
		this.paraname=ParaName;
	}
	public int getParaSort(){
		return this.parasort;
	}
	public void setParaSort(int ParaSort){
		this.parasort=ParaSort;
	}

}