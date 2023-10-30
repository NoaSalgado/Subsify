package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISharedSubService;
import com.campusdual.model.core.dao.SharedSubDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service("SharedSubService")
public class SharedSubService implements ISharedSubService {
    @Autowired
    private SharedSubDao sharedSubDao;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;
    @Override
    public EntityResult sharedSubQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.sharedSubDao, keysValues, attributes);
    }

    @Override
    public EntityResult sharedSubInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.insert(this.sharedSubDao, attributes);
    }

    @Override
    public EntityResult sharedSubUpdate(Map<String, Object> attributes, Map<String, Object>keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.sharedSubDao, attributes, keyValues);
    }

    @Override
    public EntityResult sharedSubDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.sharedSubDao, keyValues);
    }

}
