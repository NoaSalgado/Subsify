package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISubscriptionService;
import com.campusdual.model.core.dao.*;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.*;

@Lazy
@Service("SubscriptionService")
public class SubscriptionService implements ISubscriptionService {

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Autowired
    private FrequencyService frequencyService;

    @Autowired
    private SubLapseService subsLapseService;

    @Autowired
    private PlanPriceService planPriceService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private PlanService planService;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    @Autowired
    private UserSubService userSubService;



    @Override
    public EntityResult subscriptionQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> newKeyValues = new HashMap<>(keysValues);
        String username = authentication.getName();
        newKeyValues.put(UserSubDao.USER,username);
        return this.daoHelper.query(this.subscriptionDao, newKeyValues, attributes);
    }

    @Override
    public EntityResult subscriptionQueryAll(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.subscriptionDao, keysValues, attributes);
    }

    @Override
    public EntityResult subscriptionCustomInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {

        //insert category
        Map<String, Object> catQuery = new HashMap<>();
        int catId;
            if(!attributes.containsKey(CategoryDao.ID)){
                catQuery.put(CategoryDao.NAME, attributes.get(PlatformDao.NAME));
                catQuery.put(CategoryDao.CUSTOM, true);
                EntityResult categoryER = this.categoryService.categoryInsert(catQuery);
                catId =  (int)categoryER.get(CategoryDao.ID);
            }else{
                catId=(int)attributes.get(CategoryDao.ID);
            }

        //insert platform
        Map<String, Object> platfQuery = new HashMap<>();
        platfQuery.put(PlatformDao.NAME, attributes.get(PlatformDao .NAME));
        platfQuery.put(PlatformDao.CUSTOM, true);
        platfQuery.put(PlatformDao.CAT_ID, catId);
        EntityResult platformER = this.platformService.platformInsert(platfQuery);
        int platfID = (int) platformER.get(PlatformDao.ID);

        //insert plan
        Map<String, Object> planQuery = new HashMap<>();
        planQuery.put(PlanDao.PLATF_ID, platfID);
        planQuery.put(PlanDao.FR_ID, attributes.get(FrequencyDao.ID));
        planQuery.put(PlanDao.NAME, "");
        planQuery.put(PlanDao.CUSTOM, true);
        planQuery.put(PlanPriceDao.VALUE, attributes.get(SubLapseDao.PRICE));
        planQuery.put(PlanPriceDao.START, attributes.get(SubLapseDao.START));
        EntityResult planER = this.planService.planInsert(planQuery);
        int planID = (int) planER.get(PlanDao.ID);

        //Getting plan price
        Map<String, Object> planPriceKV = new HashMap<>();
        planPriceKV.put(PlanDao.ID, planID);
        List<String> planPriceAttr = new ArrayList<>();
        planPriceAttr.add(PlanPriceDao.ID);
        EntityResult planPriceER = this.planPriceService.planPriceQuery(planPriceKV, planPriceAttr);
        List planPriceArray = (ArrayList) planPriceER.get(PlanPriceDao.ID);
        int planPriceId = (int) planPriceArray.get(0);

        //insert subscription
        Map<String, Object> subscriptionQuery = new HashMap<>();
        subscriptionQuery.put(SubscriptionDao.PLAN_PRICE_ID, planPriceId);
        subscriptionQuery.put(SubLapseDao.PRICE, attributes.get(SubLapseDao.PRICE));
        subscriptionQuery.put(SubLapseDao.START, attributes.get(SubLapseDao.START));
        EntityResult subscriptionER = this.subscriptionInsert(subscriptionQuery);

        return subscriptionER;
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

        //inserting subscriptions
        Map<String, Object> attrSubsInsert = new HashMap<>();
        attrSubsInsert.put(UserSubDao.USER, username);
        attrSubsInsert.put(SubscriptionDao.ACTIVE, true);
        attrSubsInsert.put(SubscriptionDao.PLAN_PRICE_ID, attributes.get(PlanPriceDao.ID));

        EntityResult insertSubsER =  this.daoHelper.insert(this.subscriptionDao, attrSubsInsert);
        //inserting sub_lapse
        Map<String, Object> attrSubLapseInsert = new HashMap<>();
        attrSubLapseInsert.put(SubLapseDao.PRICE, attributes.get(SubLapseDao.PRICE));
        attrSubLapseInsert.put(SubLapseDao.SUBS_ID, insertSubsER.get(SubLapseDao.SUBS_ID));
        attrSubLapseInsert.put(SubLapseDao.START, attributes.get(SubLapseDao.START));

        Map<String, Object> keysValuesQuery = new HashMap<>();
        keysValuesQuery.put(PlanPriceDao.ID, attributes.get(PlanPriceDao.ID));
        EntityResult freqByPlanPriceQuery = this.planPriceService.freqByPlanPriceQuery(keysValuesQuery, List.of(FrequencyDao.ID));

        attrSubLapseInsert.put(FrequencyDao.ID, freqByPlanPriceQuery.getRecordValues(0).get(FrequencyDao.ID));
        EntityResult insertSubLapseER = this.subsLapseService.subLapseInsert(attrSubLapseInsert);
        //inserting user_sub
        Map<String, Object> attrUserSubInsert = new HashMap<>();
        attrUserSubInsert.put(UserSubDao.USER, username);
        attrUserSubInsert.put(UserSubDao.SUBS_ID,insertSubsER.get(SubLapseDao.SUBS_ID));
        this.userSubService.userSubInsert(attrUserSubInsert);
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
