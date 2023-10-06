package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISubscriptionService;
import com.campusdual.model.core.dao.FrequencyDao;
import com.campusdual.model.core.dao.SubLapseDao;
import com.campusdual.model.core.dao.SubscriptionDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Lazy
@Service("SubscriptionService")
public class SubscriptionService implements ISubscriptionService {

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Autowired
    private FrequencyService frequencyService;

    @Autowired
    private SubLapseService SubsLapseService;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    @Override
    public EntityResult subscriptionQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> newKeyValues = new HashMap<>(keysValues);
        String username = authentication.getName();
        newKeyValues.put(SubscriptionDao.USER,username);
        return this.daoHelper.query(this.subscriptionDao, newKeyValues, attributes);
    }

    @Override
    public EntityResult subscriptionQueryAll(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.subscriptionDao, keysValues, attributes);
    }

    private int getFreq(Map<String, Object> attributes){
        Map<String, Object> freqQuery = new HashMap<>();
        freqQuery.put(FrequencyDao.ID, attributes.get(FrequencyDao.ID));
        EntityResult freqER = this.frequencyService.frequencyQuery(freqQuery, List.of(FrequencyDao.VALUE));
        Map<String, Integer> freqMap = freqER.getRecordValues(0);
        return freqMap.get(FrequencyDao.VALUE);
    }

    @Override
    public EntityResult subscriptionInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Map<String, Object> attrSubsInsert = new HashMap<>();
        attrSubsInsert.put(SubscriptionDao.PLATF_ID, attributes.get(SubscriptionDao.PLATF_ID));
        attrSubsInsert.put(SubscriptionDao.FREQUENCY, attributes.get(SubscriptionDao.FREQUENCY));
        attrSubsInsert.put(SubscriptionDao.USER, username);

        EntityResult insertSubsER =  this.daoHelper.insert(this.subscriptionDao, attrSubsInsert);

        Map<String, Object> attrSubLapseInsert = new HashMap<>();
        attrSubLapseInsert.put(SubLapseDao.PRICE, attributes.get(SubLapseDao.PRICE));
        attrSubLapseInsert.put(SubLapseDao.SUBS_ID, insertSubsER.get(SubLapseDao.SUBS_ID));
        attrSubLapseInsert.put(SubLapseDao.START, attributes.get(SubLapseDao.START));
        attrSubLapseInsert.put(SubscriptionDao.FREQUENCY, attributes.get(SubscriptionDao.FREQUENCY));
        EntityResult insertSubLapseER = SubsLapseService.subLapseInsert(attrSubLapseInsert);

        return insertSubsER;
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
