package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISubLapseService;
import com.campusdual.model.core.dao.*;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.db.SQLStatementBuilder.BasicExpression;
import com.ontimize.jee.common.db.SQLStatementBuilder.BasicField;
import com.ontimize.jee.common.db.SQLStatementBuilder.BasicOperator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Lazy
@Service("SubLapseService")
public class SubLapseService implements ISubLapseService {
    @Autowired
    private SubLapseDao subLapseDao;

    @Autowired
    private FrequencyService frequencyService;
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;
    @Override
    public EntityResult subLapseQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> newKeyValues = new HashMap<>(keysValues);
        String username = authentication.getName();
        newKeyValues.put(UserSubDao.USER,username);

        return this.daoHelper.query(this.subLapseDao, newKeyValues, attributes, SubLapseDao.ACTIVE_QUERY);
    }

    @Override
    public EntityResult subLapseCatQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Map<String, Object> newKeyValues = new HashMap<>();
        newKeyValues.put(UserSubDao.USER, username);
        if(keysValues.containsKey(CategoryDao.NAME)){
            newKeyValues.put(CategoryDao.NAME, keysValues.get(CategoryDao.NAME));
        }

        return this.daoHelper.query(this.subLapseDao, newKeyValues, attributes, SubLapseDao.QUERY_CAT);
    }


    @Override
    public EntityResult subLapseToRenewQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> newKeyValues = new HashMap<>(keysValues);
        String username = authentication.getName();
        newKeyValues.put(UserSubDao.USER,username);

        return this.daoHelper.query(this.subLapseDao, newKeyValues, attributes, SubLapseDao.SUBSCRIPTIONS_TO_RENEW);
    }

    @Override
    public EntityResult subLapseQueryRenewal(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.subLapseDao, keysValues, attributes, SubLapseDao.RENEWAL_QUERY);
    }

    @Override
    public EntityResult subLapseChartCategoryQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> newKeyValues = new HashMap<>(keysValues);
        String username = authentication.getName();
        newKeyValues.put(UserSubDao.USER,username);
        return this.daoHelper.query(this.subLapseDao, newKeyValues, attributes, SubLapseDao.CHARTCATEGORY_QUERY);
    }

    private int getFreq(Map<String, Object> attributes){
        Map<String, Object> freqQuery = new HashMap<>();
        freqQuery.put(FrequencyDao.ID, attributes.get(FrequencyDao.ID));
        EntityResult freqER = this.frequencyService.frequencyQuery(freqQuery, List.of(FrequencyDao.VALUE));
        Map<String, Integer> freqMap = freqER.getRecordValues(0);
        return freqMap.get(FrequencyDao.VALUE);
    }

    @Override
    public EntityResult subLapseInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        Map<String, Object> newKeyValues = new HashMap<>(attributes);

        int freqVal = getFreq(attributes);

        Date date = (Date) attributes.get(SubLapseDao.START);
        LocalDate dateLD = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate end_date = dateLD.plusMonths(freqVal);
        newKeyValues.put(SubLapseDao.END, end_date);
        return this.daoHelper.insert(this.subLapseDao, newKeyValues);
    }

    @Override
    public EntityResult subLapseUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        if(attributes.containsKey(SubscriptionDao.ACTIVE)){
            return subscriptionService.subscriptionUpdate(attributes,keyValues );
        }
        return this.daoHelper.update(this.subLapseDao, attributes, keyValues);
    }

    @Override
    public EntityResult subLapseDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return null;
    }
}
