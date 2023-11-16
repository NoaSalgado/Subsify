package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISubLapseService;
import com.campusdual.model.core.dao.*;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    private PlanPriceService planPriceService;
    @Autowired
    private SubLapseService subLapseService;
    @Autowired
    private SubLapseCustomService subLapseCustomService;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    @Override
    public EntityResult subLapseQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> newKeyValues = new HashMap<>(keysValues);
        String username = authentication.getName();
        newKeyValues.put(UserSubDao.USER, username);

        return this.daoHelper.query(this.subLapseDao, newKeyValues, attributes, SubLapseDao.ACTIVE_QUERY);
    }

    @Override
    public EntityResult subLapseCatQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Map<String, Object> newKeyValues = new HashMap<>();
        newKeyValues.put(UserSubDao.USER, username);
        if (keysValues.containsKey(CategoryDao.NAME)) {
            newKeyValues.put(CategoryDao.NAME, keysValues.get(CategoryDao.NAME));
        }

        return this.daoHelper.query(this.subLapseDao, newKeyValues, attributes, SubLapseDao.QUERY_CAT);
    }


    @Override
    public EntityResult subLapseToRenewQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> newKeyValues = new HashMap<>(keysValues);
        String username = authentication.getName();
        newKeyValues.put(UserSubDao.USER, username);

        EntityResult subsToRenewEr = this.daoHelper.query(this.subLapseDao, newKeyValues, attributes, SubLapseDao.SUBSCRIPTIONS_TO_RENEW);
        int erSize = subsToRenewEr.calculateRecordNumber();
        EntityResult newEntityResult = new EntityResultMapImpl(List.of(SubLapseDao.ID,
                PlatformDao.NAME,
                PlatformDao.PLATF_IMAGE,
                SubLapseDao.END,
                SubLapseDao.PRICE,
                SubscriptionDao.ACTIVE,
                PlatformDao.PLATF_LINK,
                SubLapseCustomDao.SLC_PRICE,
                SubLapseCustomDao.SLC_END,
                PlanPriceDao.VALUE,
                PlanPriceDao.END,
                PlanPriceDao.ID,
                PlanDao.ID,
                SubscriptionDao.ID
        ));
        for (int i = 0; i < erSize; i++) {
            Map<String, Object> subRecord = subsToRenewEr.getRecordValues(i);
            Date planPriceEnd = (Date) subRecord.get(PlanPriceDao.END);
            Date subLapseEnd = (Date) subRecord.get(SubLapseDao.END);
            int subsID=(int)subRecord.get(SubscriptionDao.ID);
            int planId = (int) subRecord.get(PlanDao.ID);
            Map<String,Object> subLapseCustomKV=new HashMap<>();
            subLapseCustomKV.put(SubscriptionDao.ID,subsID);
            subLapseCustomKV.put(SubLapseDao.END,subLapseEnd);
            EntityResult subLapseCustomER=this.subLapseCustomService.customPriceQuery(subLapseCustomKV,
                    List.of(SubLapseCustomDao.SLC_PRICE));
            BigDecimal newCustomPrice=(BigDecimal) subLapseCustomER.getRecordValues(0).get(SubLapseCustomDao.SLC_PRICE);
            if(newCustomPrice!=null){
                subRecord.put(SubLapseCustomDao.SLC_PRICE,newCustomPrice);
            }



            if (planPriceEnd != null && planPriceEnd.before(subLapseEnd)) {
                Map<String, Object> planPriceQueryKV = new HashMap<>();
                planPriceQueryKV.put(PlanPriceDao.PLAN_ID, planId);
                SQLStatementBuilder.BasicField planPriceEndField = new SQLStatementBuilder.BasicField(PlanPriceDao.END);
                SQLStatementBuilder.BasicExpression bexp1 = new SQLStatementBuilder.BasicExpression(planPriceEndField,
                        SQLStatementBuilder.BasicOperator.NULL_OP, null);
                planPriceQueryKV.put(SQLStatementBuilder.ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY, bexp1);
                //TODO si hay varios cambios de precio en los últimos meses?
                EntityResult planPriceEr = this.planPriceService.planPriceQuery(planPriceQueryKV,
                        List.of(PlanPriceDao.VALUE));
                BigDecimal newPlanPrice = (BigDecimal) planPriceEr.getRecordValues(0).get(PlanPriceDao.VALUE);
                subRecord.put(PlanPriceDao.VALUE, newPlanPrice);
            }
            newEntityResult.addRecord(subRecord, i);
            newEntityResult.setCode(EntityResult.OPERATION_SUCCESSFUL);


        }
        return newEntityResult;
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
        newKeyValues.put(UserSubDao.USER, username);
        return this.daoHelper.query(this.subLapseDao, newKeyValues, attributes, SubLapseDao.CHARTCATEGORY_QUERY);
    }

    private int getFreq(Map<String, Object> attributes) {
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

        if (attributes.containsKey(SubscriptionDao.ACTIVE)) {
            return subscriptionService.subscriptionUpdate(attributes, keyValues);
        }
        Map<String, Object> subLapseKV = new HashMap<>();
        int subLapseId = (int)keyValues.get(SubLapseDao.ID);
        subLapseKV.put(SubLapseDao.ID, subLapseId);

        EntityResult subLapseER = this.subLapseService.subLapseQuery(
                subLapseKV,
                List.of(SubscriptionDao.ID,
                        SubLapseDao.START));
        Map<String,Object> subsKV=new HashMap<>();
        int subsId=(int)subLapseER.getRecordValues(0).get(SubscriptionDao.ID);
        subsKV.put(SubscriptionDao.ID,subsId);
        EntityResult subsER=this.subscriptionService.subscriptionQuery(subsKV,List.of(SubscriptionDao.PLAN_PRICE_ID));
        Map<String,Object> planPriceKV=new HashMap<>();
        planPriceKV.put(PlanPriceDao.ID,subsER.getRecordValues(0).get(SubscriptionDao.PLAN_PRICE_ID));

        EntityResult standardPricePlanER = this.planPriceService.planPriceQuery(planPriceKV, List.of(PlanPriceDao.VALUE));
        boolean isPriceCustomized = !Objects.equals(standardPricePlanER.getRecordValues(0).get(PlanPriceDao.VALUE),
                new BigDecimal(String.valueOf(attributes.get(SubLapseDao.PRICE))));
        if(isPriceCustomized){
            Map<String, Object> attrCustomSubLapseInsert = new HashMap<>();
            attrCustomSubLapseInsert.put(SubLapseCustomDao.SUBS_ID,subsId);
            attrCustomSubLapseInsert.put(SubLapseCustomDao.SLC_PRICE, attributes.get(SubLapseDao.PRICE));
            attrCustomSubLapseInsert.put(SubLapseCustomDao.SLC_START, subLapseER.getRecordValues(0).get(SubLapseDao.START));

            if(attributes.containsKey(SubLapseCustomDao.SLC_END)){
                attrCustomSubLapseInsert.put(SubLapseCustomDao.SLC_END, attributes.get(SubLapseCustomDao.SLC_END));
            }
            this.subLapseCustomService.subLapseCustomInsert(attrCustomSubLapseInsert);
        }




        return this.daoHelper.update(this.subLapseDao, attributes, keyValues);

        }

    @Override
    public EntityResult subLapseDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return null;
    }



}
