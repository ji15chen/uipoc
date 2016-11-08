package com.example.dbman.db.genupdate.daoimpl;
import com.example.dbman.core.BaseDatabase;
import com.example.dbman.core.Constants;
import com.example.dbman.db.genupdate.schema.SysParameter;
import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.BaseDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.example.dbman.db.genupdate.dao.EquipTypeDao;
import com.example.dbman.db.genupdate.schema.EquipType;
public class EquipTypeDaoImpl extends BaseDaoImpl<EquipType,java.util.UUID> implements EquipTypeDao{
	private  QueryBuilder<EquipType,java.util.UUID>  queryBuilder = null ;

	public EquipTypeDaoImpl (ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, EquipType.class );
		queryBuilder = this.queryBuilder();
	}

	@Override
	public List<EquipType> findByParentUUID(UUID parentUUID) throws SQLException {
		return queryForEq("SupPkTypeID", parentUUID);
	}

	@Override
	public List<EquipType> findBySimilarTypeName(String typeName) throws SQLException{
		return findBySimilarTypeNameQuery(typeName).query();
	}

	@Override
	public Where<EquipType,UUID> getLeafEquipByParentUUIDQuery(UUID ... parentUUIDs) throws SQLException {
		final Where<EquipType,UUID> nullWhere = queryBuilder.where().in("PkTypeID", Constants.NULL_UUID);
		HashMap<UUID,UUID> uuids = new HashMap<UUID,UUID>();
		for (UUID parentUUID:parentUUIDs) {
			tryAdd(uuids, parentUUID);
		}
		return (uuids.size()>0)?queryBuilder.where().in("PkTypeID", uuids):nullWhere;
	}
	@Override
	public Where<EquipType,UUID> getLeafEquipByParentObjectsQuery(Iterator<EquipType> iterator) throws SQLException{
		final Where<EquipType,UUID> nullWhere = queryBuilder.where().in("PkTypeID", Constants.NULL_UUID);
		HashMap<UUID,UUID> uuids = new HashMap<UUID,UUID>();
		while (iterator.hasNext()){
			tryAdd(uuids, iterator.next().getPkTypeID());
		}
		return (uuids.size()>0)?queryBuilder.where().in("PkTypeID", uuids):nullWhere;
	}

	private  void tryAdd(HashMap<UUID,UUID>uuids, final UUID pId) throws SQLException{
		List<EquipType> subs = findByParentUUID(pId);
		if ( (subs == null) || (subs.size() <= 0) ){
			uuids.put(pId,pId);
		}else{
			for (EquipType equipType:subs){
				tryAdd(uuids, equipType.getPkTypeID());
			}
		}
	}

	@Override
	public Where<EquipType,UUID> findBySimilarTypeNameQuery(String typeName) throws SQLException {
		String arg;
		if (typeName.length()> 0){
			arg = "%"+typeName+"%";
		}else{
			arg = "%";
		}
		return  queryBuilder.where().like("TypeName", arg);
	}

	@Override
	public CloseableIterator<EquipType> lookupBriefEquipTypeInfo(Where<EquipType,UUID> query)  throws SQLException {
		SysParameterDaoImpl sysParameterDao = (SysParameterDaoImpl)BaseDatabase.getInstance().getDaoImpl("SysParameter");
		QueryBuilder<SysParameter, UUID> sysParameterUUIDQueryBuilder = sysParameterDao.queryBuilder();
		QueryBuilder<EquipType,UUID> joinedQB = queryBuilder.join(sysParameterUUIDQueryBuilder);
		joinedQB.setWhere(query);
		return joinedQB.iterator();
	}

	@Override
	public CloseableIterator<EquipType> lookupDetailedEquipTypeInfo(Where<EquipType,UUID> query) throws SQLException{
		return null;
	}
}
