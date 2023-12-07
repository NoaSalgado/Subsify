package com.campusdual.model.core.service;

import com.campusdual.api.core.service.IUserSubService;
import com.campusdual.model.core.dao.*;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.db.SQLStatementBuilder.BasicExpression;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ontimize.jee.common.db.SQLStatementBuilder.*;

@Lazy
@Service("UserSubService")
public class UserSubService implements IUserSubService {
    @Autowired
    private UserSubDao userSubDao;
    @Autowired
    private SubLapseService subLapseService;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    @Override
    public EntityResult userSubQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Map<String, Object> queryUserSubKV = new HashMap<>();
        int subLapseId = Integer.parseInt((String) keysValues.get(SubLapseDao.ID));
        queryUserSubKV.put(SubLapseDao.ID, subLapseId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        BasicField field = new BasicField(UserSubDao.USER);
        BasicExpression bexp = new BasicExpression(field, BasicOperator.NOT_EQUAL_OP, username);

        BasicExpression bexp1 = new BasicExpression(field, BasicOperator.NULL_OP, null);

        BasicExpression bexp3 = new BasicExpression(bexp, BasicOperator.OR_OP, bexp1);
        queryUserSubKV.put(ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY, bexp3);

        EntityResult er =  this.daoHelper.query(this.userSubDao, queryUserSubKV, attributes);
        int erSize = er.calculateRecordNumber();
        for(int i = 0; i < erSize; i++){
            Map<String, Object> record = er.getRecordValues(i);
            er.deleteRecord(i);
            if(record.get(UserSubDao.USER) == null){
                record.put(UserSubDao.USER, "");
                record.put("USER_NAME", record.get(UserSubDao.USER_SUB_VIRTUAL));
            }

            if(record.get(UserSubDao.USER_SUB_VIRTUAL) == null){
                record.put(UserSubDao.USER_SUB_VIRTUAL, "");
                record.put("USER_NAME", record.get(UserSubDao.USER));
            }
            er.addRecord(record);
        }
        return er;
    }

    @Override
    public EntityResult userSubInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        if(!attributes.containsKey(UserSubDao.USER_SUB_VIRTUAL) && !attributes.containsKey(UserSubDao.USER)){
            EntityResult erError = this.throwError("ERROR_USERSUB_NO_USER_MESSAGE");
            return erError;
        }

       if(attributes.containsKey(UserSubDao.USER_SUB_VIRTUAL) && attributes.containsKey(UserSubDao.USER)  && !attributes.get(UserSubDao.USER_SUB_VIRTUAL).toString().equals("") ){
            EntityResult erError =  this.throwError("ERROR_USERSUB_INSERT_BOTH_MESSAGE");
            return erError;
        }

     if(this.sharingWithUser(attributes)){
         EntityResult erError = this.throwError("ERROR_USERSUB_ALREADY_SHARING_MESSAGE");
         return erError;
       }

            int subsId;
            Map<String, Object> insertUserSubAttr = new HashMap<>();
            if (attributes.containsKey(SubLapseDao.ID)){
                //Getting subs_id by sub_lapse_id
                subsId = this.getSubsId(attributes);

                if(attributes.containsKey(UserSubDao.USER_SUB_VIRTUAL) && !attributes.get(UserSubDao.USER_SUB_VIRTUAL).toString().trim().equals("")){
                    insertUserSubAttr.put(UserSubDao.USER_SUB_VIRTUAL,attributes.get(UserSubDao.USER_SUB_VIRTUAL));
                    insertUserSubAttr.put(SubscriptionDao.ID, subsId);
                    return this.daoHelper.insert(this.userSubDao, insertUserSubAttr);
                }
            }else {
                subsId = (int) attributes.get(SubLapseDao.SUBS_ID);
            }

            insertUserSubAttr.put(SubscriptionDao.ID, subsId);
            insertUserSubAttr.put(UserSubDao.USER,attributes.get(UserSubDao.USER));
        return this.daoHelper.insert(this.userSubDao, insertUserSubAttr);
    }

    @Override
    public EntityResult userSubUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.userSubDao, attributes, keyValues);
    }

    @Override
    public EntityResult userSubDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.userSubDao, keyValues);
    }

    private EntityResult throwError(String errorMessage){
        EntityResult errorEr = new EntityResultMapImpl();
        errorEr.setCode(EntityResult.OPERATION_WRONG);
        errorEr.setMessage(errorMessage);
        return errorEr;
    }

    private int getSubsId(Map<String, Object> attributes){
        Map<String, Object> querySubLapseKV = new HashMap<>();
        int subLapseId = Integer.parseInt((String) attributes.get(SubLapseDao.ID));
        querySubLapseKV.put(SubLapseDao.ID, subLapseId);

        List<String> querySubLapseAttr = new ArrayList<>();
        querySubLapseAttr.add(SubLapseDao.SUBS_ID);
        EntityResult er = this.subLapseService.subLapseQuery(querySubLapseKV, querySubLapseAttr);
        Map<String, Object> subLapse = er.getRecordValues(0);
        return (int) subLapse.get(SubLapseDao.SUBS_ID);
    }

    private boolean sharingWithUser(Map<String, Object> attributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if(attributes.containsKey(UserSubDao.USER) && attributes.get(UserSubDao.USER).equals(username)){
            return false;
        }
        Map<String, Object> userSubKv = new HashMap<>();
        List<String>userSubAttr = new ArrayList<>();
        if(attributes.containsKey(UserSubDao.USER_SUB_VIRTUAL)){
            userSubKv.put(UserSubDao.USER_SUB_VIRTUAL, attributes.get(UserSubDao.USER_SUB_VIRTUAL));
            userSubAttr.add(UserSubDao.USER_SUB_VIRTUAL);
        }else{
            userSubKv.put(UserSubDao.USER, attributes.get(UserSubDao.USER));
            userSubAttr.add(UserSubDao.USER);
        }
        int subsId = this.getSubsId(attributes);

        userSubKv.put(UserSubDao.SUBS_ID, subsId);
        userSubAttr.add(UserSubDao.SUBS_ID);

        EntityResult userSubER = this.daoHelper.query(this.userSubDao, userSubKv, userSubAttr);
        return userSubER.calculateRecordNumber() > 0;
    }
}
