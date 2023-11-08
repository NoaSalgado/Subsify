
package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ICategoryService;
import com.campusdual.model.core.dao.CategoryDao;
import com.campusdual.model.core.dao.SubLapseDao;
import com.campusdual.model.core.dao.UserSubDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.common.security.PermissionsProviderSecured;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Lazy
@Service("CategoryService")
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    @Override
    @Secured({ PermissionsProviderSecured.SECURED })
    public EntityResult categoryQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Map<String, Object> queryKV = new HashMap<>();
        queryKV.put(CategoryDao.CUSTOM, false);
        return this.daoHelper.query(this.categoryDao, queryKV, attributes);
    }

    @Override
   // @Secured({ PermissionsProviderSecured.SECURED })
    public EntityResult categoryActiveQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Map<String, Object> newKeysValues = new HashMap<>();
        newKeysValues.put(CategoryDao.ACTIVE, true);
        newKeysValues.put(CategoryDao.CUSTOM,false);
        return this.daoHelper.query(this.categoryDao, newKeysValues, attributes);
    }

    /*
    public EntityResult catIdBycatNameQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Map<String, Object> newKeysValues = new HashMap<>();
        newKeysValues.put(UserSubDao.USER, username);
        newKeysValues.put(CategoryDao.NAME, keysValues.get(CategoryDao.NAME));

        return this.daoHelper.query(this.categoryDao, newKeysValues, attributes);
    }*/

    public EntityResult categoryInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.insert(this.categoryDao, attributes);
    }

    @Override
    @Secured({ PermissionsProviderSecured.SECURED })
    public EntityResult categoryUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.categoryDao, attributes, keyValues);
    }

    @Override
    @Secured({ PermissionsProviderSecured.SECURED })
    public EntityResult categoryDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.categoryDao, keyValues);
    }


}