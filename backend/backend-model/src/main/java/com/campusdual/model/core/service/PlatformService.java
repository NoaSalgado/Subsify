package com.campusdual.model.core.service;

import com.campusdual.api.core.service.IPlatformService;
import com.campusdual.model.core.dao.PlatformDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Lazy
@Service("PlatformService")
public class PlatformService implements IPlatformService {
    @Autowired
    private PlatformDao platformDao;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    @Override
    public EntityResult platformQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Map<String, Object> queryKV = new HashMap<>();
        queryKV.put(PlatformDao.CUSTOM, false);
        if(keysValues.containsKey(PlatformDao.ID)){
            queryKV.put(PlatformDao.ID, keysValues.get(PlatformDao.ID));
        } else {
            queryKV.put(PlatformDao.PLATF_ACTIVE, true);
        }

        return this.daoHelper.query(this.platformDao, queryKV, attributes, PlatformDao.DEFAULT_QUERY);
    }

    @Override
    public EntityResult platformAdminQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Map<String, Object> queryKV = new HashMap<>();
        queryKV.put(PlatformDao.CUSTOM, false);
        return this.daoHelper.query(this.platformDao, queryKV, attributes, PlatformDao.DEFAULT_QUERY);
    }

    @Override
    public EntityResult usersByCustomPlatQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.platformDao, keysValues, attributes, PlatformDao.USERS_BY_CUSTOM_PLAT_QUERY);
    }

    @Override
    public EntityResult platformInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.insert(this.platformDao, attributes);
    }

    @Override
    public EntityResult platformUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.platformDao, attributes, keyValues);
    }

    @Override
    public EntityResult platformDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.platformDao, keyValues);
    }
}