package com.campusdual.model.core.service;

import com.campusdual.api.core.service.IUserSubService;
import com.campusdual.model.core.dao.UserSubDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service("UserSubService")
public class UserSubService implements IUserSubService {
    @Autowired
    private UserSubDao userSubDao;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;


    @Override
    public EntityResult userSubQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.userSubDao, keysValues, attributes);
    }

    @Override
    public EntityResult userSubInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.insert(this.userSubDao, attributes);
    }

    @Override
    public EntityResult userSubUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.userSubDao, attributes, keyValues);
    }

    @Override
    public EntityResult userSubDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.userSubDao, keyValues);
    }
}
