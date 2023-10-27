package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISharedSubsService;
import com.campusdual.model.core.dao.SharedSubsDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service("SharedSubsService")
public class SharedSubsService implements ISharedSubsService {
    @Autowired
    private SharedSubsDao sharedSubsDao;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;
    @Override
    public EntityResult sharedSubsQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.sharedSubsDao, keysValues, attributes);
    }

    @Override
    public EntityResult sharedSubsInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.insert(this.sharedSubsDao, attributes);
    }

    @Override
    public EntityResult sharedSubsUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.sharedSubsDao, attributes, keyValues);
    }

    @Override
    public EntityResult sharedSubsDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.sharedSubsDao, keyValues);
    }
}
