package com.campusdual.model.core.jobs;


import com.campusdual.model.core.dao.*;
import com.campusdual.model.core.service.PlanService;
import com.campusdual.model.core.service.SubLapseCustomService;
import com.campusdual.model.core.service.SubLapseService;
import com.campusdual.model.core.service.SubscriptionService;
import com.ontimize.jee.common.db.Entity;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ScheduledTask {
    @Autowired
    SubLapseService subLapseService;

    @Autowired
    PlanService planService;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    SubLapseCustomService subLapseCustomService;

    private SQLStatementBuilder.BasicExpression filterByActiveAndDate(String endDate, String active) {
        SQLStatementBuilder.BasicField endDateField = new SQLStatementBuilder.BasicField(endDate);
        SQLStatementBuilder.BasicExpression dateBE = new SQLStatementBuilder.BasicExpression(endDateField, SQLStatementBuilder.BasicOperator.LESS_OP, LocalDate.now());

        SQLStatementBuilder.BasicField activeField = new SQLStatementBuilder.BasicField(active);
        SQLStatementBuilder.BasicExpression activeBE = new SQLStatementBuilder.BasicExpression(activeField, SQLStatementBuilder.BasicOperator.EQUAL_OP, true);

        return new SQLStatementBuilder.BasicExpression(dateBE, SQLStatementBuilder.BasicOperator.AND_OP, activeBE);
    }

    //Obtains new sub_lapse_price by getting active plans found by plan id
    public Map<String, Object> getActivePlanByPlanId(Map<String, Object> planId) {
        List<String> attrs = new ArrayList<>();
        attrs.add(PlanPriceDao.VALUE);
        attrs.add(PlanPriceDao.ID);
        EntityResult er = planService.planActiveQuery(planId, attrs);
        return er.getRecordValues(0);
    }

    public void updateSubsPlanPriceId(Map<String, Object> subsRegistry, Map<String, Object> newPlanPrice) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(SubscriptionDao.PLAN_PRICE_ID, newPlanPrice.get(PlanPriceDao.ID));

        Map<String, Object> keyvalues = new HashMap<>();
        keyvalues.put(SubscriptionDao.ID, subsRegistry.get(SubLapseDao.SUBS_ID));

        subscriptionService.subscriptionUpdate(attributes, keyvalues);
    }

    public EntityResult getCustomSub(Map<String, Object> subsRegistry) {
        List<String> attributes = Arrays.asList(
                SubLapseCustomDao.SUBS_ID,
                SubLapseCustomDao.SLC_PRICE,
                SubLapseCustomDao.SLC_END
        );
        Map<String, Object> customSubQueryKV = new HashMap<>();
        customSubQueryKV.put(SubLapseCustomDao.SUBS_ID, subsRegistry.get(SubLapseDao.SUBS_ID));
        EntityResult customSubER = subLapseCustomService.subLapseCustomQuery(customSubQueryKV, attributes);
        return customSubER;
    }

    public boolean hasCustomPrice(Map<String, Object> subsRegistry) {
        EntityResult customSubER = this.getCustomSub(subsRegistry);
        return customSubER.calculateRecordNumber() > 0;
    }

    public boolean keepCustomPrice(Map<String, Object> subsRegistry, Date renewDate) {
        Map<String, Object> customSub = this.getCustomSub(subsRegistry).getRecordValues(0);
        return customSub.get(SubLapseCustomDao.SLC_END) == null || ((Date) customSub.get(SubLapseCustomDao.SLC_END)).after(renewDate);
    }

    @Scheduled(fixedRate = 1000)
    public void scheduleTask() {
        try {
            List<String> columns = Arrays.asList(
                    SubLapseDao.ID,
                    SubLapseDao.END,
                    SubLapseDao.START,
                    SubLapseDao.SUBS_ID,
                    SubLapseDao.PRICE,
                    PlanDao.FR_ID,
                    PlanDao.ID);
            //SubscriptionDao.PLAN_PRICE_ID,
            //PlanPriceDao.VALUE);

            EntityResult subscriptionsToUpdate = subLapseService.subLapseQueryRenewal(new HashMap<>(), columns);

            int erSize = subscriptionsToUpdate.calculateRecordNumber();
            if (erSize > 0 ){
                System.out.println("here");
                subscriptionsToUpdate.getRecordValues(0).get(SubLapseDao.PRICE)
                        .equals(subscriptionsToUpdate.getRecordValues(0).get(PlanPriceDao.VALUE));
            }
            for (int i = 0; i < erSize; i++) {
                Map<String, Object> subsRegistry = subscriptionsToUpdate.getRecordValues(i);
                Map<String, Object> planQueryAttrs = new HashMap<>();
                planQueryAttrs.put(PlanDao.ID, subsRegistry.get(PlanDao.ID));
                Map<String, Object> newPlanPrice = getActivePlanByPlanId(planQueryAttrs);

                updateSubsPlanPriceId(subsRegistry, newPlanPrice);

                Map<String, Object> attrs = new HashMap<>();
                attrs.put(SubLapseDao.SUBS_ID, subsRegistry.get(SubLapseDao.SUBS_ID));
                attrs.put(PlanDao.FR_ID, subsRegistry.get(PlanDao.FR_ID));

                java.sql.Date oldEndDate = (java.sql.Date) subsRegistry.get(SubLapseDao.END);
                LocalDate dateLD = oldEndDate.toLocalDate();
                LocalDate newDateLD = dateLD.plusDays(1);
                //pasar a date

                Date newDate = Date.from((newDateLD.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                attrs.put(SubLapseDao.START, newDate);

                if (this.hasCustomPrice(subsRegistry) && this.keepCustomPrice(subsRegistry, newDate)) {
                    EntityResult customSub = this.getCustomSub(subsRegistry);
                    attrs.put(SubLapseDao.PRICE, customSub.getRecordValues(0).get(SubLapseCustomDao.SLC_PRICE));
                } else {
                    attrs.put(SubLapseDao.PRICE, newPlanPrice.get(PlanPriceDao.VALUE));
                }
                EntityResult result = subLapseService.subLapseInsert(attrs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            EntityResult res = new EntityResultMapImpl();
            res.setCode(EntityResult.OPERATION_WRONG);
        }
    }
}
