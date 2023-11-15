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
import java.math.BigDecimal;
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
    @Autowired
    private SubLapseCustomService subLapseCustomService;



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

    private String convertToTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

    //When empty category on custom insert checks if this category exists in database, if it does there is no insert,
    //other case it does insert a new category with that platform name.
    private int insertEmptyCategory(String platformName, Map<String, Object> attributes){
        //Getting categories of actual user
        EntityResult categoryListER = this.subsLapseService.subLapseCatQuery(
                new HashMap<>(),
                new ArrayList<String>(List.of(CategoryDao.NAME)));
        int categoryListERSize = categoryListER.calculateRecordNumber();
        List<String> categoryNames = new ArrayList<>();
        for (int i = 0; i < categoryListERSize; i++) {
            categoryNames.add((String) categoryListER.getRecordValues(i).get(CategoryDao.NAME));
        }

        int catId;
        Map<String, Object> catQuery = new HashMap<>();

        if (categoryNames.contains(platformName)){
            //getting category id by name
            Map<String, Object> catKV = new HashMap<>();
            catKV.put(CategoryDao.NAME, platformName);
            EntityResult categoryIDER = this.subsLapseService.subLapseCatQuery(catKV,
                    new ArrayList<String>(List.of(CategoryDao.ID)));
            catId=(int) categoryIDER.getRecordValues(0).get(CategoryDao.ID);
        }else{
            catQuery.put(CategoryDao.NAME, platformName);
            catQuery.put(CategoryDao.CUSTOM, true);
            EntityResult categoryER = this.categoryService.categoryInsert(catQuery);
            catId =  (int)categoryER.get(CategoryDao.ID);
        }
        return catId;
    }
    @Override
    public EntityResult subscriptionCustomInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {

        String platformNameNoFormat = (String) attributes.get(PlatformDao.NAME);
        String platformName = this.convertToTitleCase(platformNameNoFormat);

        //insert category
        int catId;
            if(!attributes.containsKey(CategoryDao.ID)){
                catId = insertEmptyCategory(platformName, attributes);
            }else{
                catId=(int)attributes.get(CategoryDao.ID);
            }

        //insert platform
        Map<String, Object> platfQuery = new HashMap<>();
        platfQuery.put(PlatformDao.NAME, platformName);
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

    @Override
    public EntityResult subCustomPricesQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Map<String, Object> subLapseKV = new HashMap<>();
        int subLapseId = Integer.parseInt((String)keysValues.get(SubLapseDao.ID));
        subLapseKV.put(SubLapseDao.ID, subLapseId);

        EntityResult subLapseER = this.subsLapseService.subLapseQuery(
                subLapseKV,
                List.of(SubscriptionDao.ID));

        Map<String, Object> subLapseCustomKV = new HashMap<>();
        subLapseCustomKV.put(SubscriptionDao.ID, subLapseER.getRecordValues(0).get(SubscriptionDao.ID));
        return this.subLapseCustomService.subLapseCustomBySubIdQuery(subLapseCustomKV,
               attributes);
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

        // inserting sub_lapse_custom if is the case
        EntityResult standardPricePlanER = this.planPriceService.planPriceQuery(keysValuesQuery, List.of(PlanPriceDao.VALUE));

        boolean isPriceCustomized = !Objects.equals(standardPricePlanER.getRecordValues(0).get(PlanPriceDao.VALUE), new BigDecimal(String.valueOf(attributes.get(SubLapseDao.PRICE))));
        if(isPriceCustomized){
            Map<String, Object> attrCustomSubLapseInsert = new HashMap<>();
            attrCustomSubLapseInsert.put(SubLapseCustomDao.SUBS_ID,insertSubsER.get(SubLapseDao.SUBS_ID));
            attrCustomSubLapseInsert.put(SubLapseCustomDao.SLC_PRICE, attributes.get(SubLapseDao.PRICE));
            attrCustomSubLapseInsert.put(SubLapseCustomDao.SLC_START, attributes.get(SubLapseDao.START));

            if(attributes.containsKey(SubLapseCustomDao.SLC_END)){
                attrCustomSubLapseInsert.put(SubLapseCustomDao.SLC_END, attributes.get(SubLapseCustomDao.SLC_END));
            }
            this.subLapseCustomService.subLapseCustomInsert(attrCustomSubLapseInsert);
        }
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
