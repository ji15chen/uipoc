package com.example.dbman.db.genupdate.dao;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.example.dbman.db.genupdate.schema.EquipType;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public interface EquipTypeDao extends Dao<EquipType,java.util.UUID>{
    /*
    *
    * @show 列出属于父UUID的所有子UUID
    * */
    List<EquipType> findByParentUUID(UUID parentUUID) throws SQLException;
    /*
    *
    * @show 列出属于相似名称的所有结点
    * */
    List<EquipType> findBySimilarTypeName(final String typeName) throws SQLException;

    /*
    *
    * @show 产生UUID下的所有叶子节点查询
    * */
    Where<EquipType,UUID> getLeafEquipByParentUUIDQuery(UUID ... parentUUIDs) throws SQLException;
    Where<EquipType,UUID> getLeafEquipByParentObjectsQuery(Iterator<EquipType> iterator) throws SQLException;

    /*
    *
    * @show 产生相似名称的查询
    * */
    Where<EquipType,UUID> findBySimilarTypeNameQuery(final String typeName) throws SQLException;
    /*
    *
    * @show 产生概要装备信息
    * */
    CloseableIterator<EquipType> lookupBriefEquipTypeInfo(Where<EquipType,UUID> query)  throws SQLException;
    /*
*
* @show 产生详细装备信息
* */
    CloseableIterator<EquipType> lookupDetailedEquipTypeInfo(Where<EquipType,UUID> query)  throws SQLException;
}
