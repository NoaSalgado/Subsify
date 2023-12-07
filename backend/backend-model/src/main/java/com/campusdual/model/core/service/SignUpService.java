package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISignUpService;
import com.campusdual.model.core.dao.UserDao;
import com.campusdual.model.core.dao.UserRoleDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Lazy
@Service("SignUpService")
public class SignUpService implements ISignUpService {
    @Autowired
    private UserDao userDao;
    @Autowired UserRoleDao userRoleDao;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;
    @Override
    public EntityResult signUpInsert(Map<String, Object> attrMap) throws OntimizeJEERuntimeException {
        try{
            String password = (String) attrMap.get(UserDao.PASSWORD);
            String confirmPassword = (String) attrMap.get("CONFIRM_PASS");

            if (!password.equals(confirmPassword)){
                EntityResult errorEr = new EntityResultMapImpl();
                errorEr.setCode(EntityResult.OPERATION_WRONG);
                errorEr.setMessage("ERROR_NOT_MATCHING_PASSWORDS");
                return errorEr;
            }
            EntityResult insertQuery = this.daoHelper.insert(userDao, attrMap);

            String user = (String) attrMap.get(UserDao.ID);
            Map<String, Object> userRoleKV = new HashMap<>();
            userRoleKV.put(UserDao.ID, user);
            userRoleKV.put(UserRoleDao.ID_ROLENAME, 1);

            EntityResult insertUserRoleQuery = userRoleDao.insert(userRoleKV);

            return  insertQuery;
        }catch (org.springframework.dao.DuplicateKeyException exception){
            EntityResult errorEr = new EntityResultMapImpl();
            errorEr.setCode(EntityResult.OPERATION_WRONG);
            errorEr.setMessage("ERROR_DUPLICATE_USER_NAME");
            return errorEr;
        }
    }
}
