package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISubscriptionService;
import com.campusdual.model.core.dao.FrequencyDao;
import com.campusdual.model.core.dao.SubscriptionDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Lazy
@Service("SubscriptionService")
public class SubscriptionService implements ISubscriptionService {

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Autowired
    private FrequencyService frequencyService;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    @Override
    public EntityResult subscriptionQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Map<String, Object> newKeyValues = new HashMap<>(keysValues);
        newKeyValues.put("USER_",username);
        EntityResult subscriptionER= this.daoHelper.query(this.subscriptionDao, newKeyValues, attributes);

       int logER= subscriptionER.calculateRecordNumber();
        Map< String,Object>  subsRegistry =new HashMap<>();

        for(int i=0;i<logER;i++){
            subsRegistry=subscriptionER.getRecordValues(i);
            Date endDate=(Date)subsRegistry.get(SubscriptionDao.ATTR_SUBS_END_DATE);
           LocalDate endDateLD = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
           Map<String,Object> attrs=new HashMap<>();
           attrs.put(SubscriptionDao.ATTR_SUBS_ACTIVE,false);
           Map<String,Object> keys=new HashMap<>();
           keys.put(SubscriptionDao.ID,subsRegistry.get(SubscriptionDao.ID));
           if(endDateLD.isBefore(LocalDate.now())){
               this.subscriptionUpdate(attrs,keys);


           }


       }


        return subscriptionER;
    }

    @Override
    public EntityResult subscriptionInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Map<String, Object> newKeyValues = new HashMap<>(attributes);
        newKeyValues.put("USER_",username);


        Map<String, Object> freqQuery = new HashMap<>();
        freqQuery.put(FrequencyDao.ATTR_FR_ID, attributes.get(FrequencyDao.ATTR_FR_ID));
        EntityResult freqER = this.frequencyService.frequencyQuery(freqQuery, List.of(FrequencyDao.ATTR_FR_VALUE));
        Map<String, Integer> freqMap = freqER.getRecordValues(0);
        int freqVal = freqMap.get(FrequencyDao.ATTR_FR_VALUE);

        Date date = (Date) attributes.get(SubscriptionDao.ATTR_SUBS_START_DATE);
        LocalDate dateLD = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        LocalDate end_date = dateLD.plusMonths(freqVal);
        newKeyValues.put(SubscriptionDao.ATTR_SUBS_END_DATE, end_date);
        return this.daoHelper.insert(this.subscriptionDao, newKeyValues);
    }

    @Override
    public EntityResult subscriptionUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.subscriptionDao, attributes, keyValues);
    }

    @Override
    public EntityResult subscriptionDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.subscriptionDao, keyValues);
    }
}
