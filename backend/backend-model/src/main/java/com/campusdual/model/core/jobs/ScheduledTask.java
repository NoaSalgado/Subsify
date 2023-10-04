package com.campusdual.model.core.jobs;


import com.campusdual.model.core.dao.SubscriptionDao;
import com.campusdual.model.core.service.SubscriptionService;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ScheduledTask {

    @Autowired
    SubscriptionService subscriptionService;

    private SQLStatementBuilder.BasicExpression filterByActiveAndDate(String endDate, String active) {
        SQLStatementBuilder.BasicField endDateField = new SQLStatementBuilder.BasicField(endDate);
        SQLStatementBuilder.BasicExpression dateBE = new SQLStatementBuilder.BasicExpression(endDateField, SQLStatementBuilder.BasicOperator.LESS_OP, LocalDate.now());

        SQLStatementBuilder.BasicField activeField = new SQLStatementBuilder.BasicField(active);
        SQLStatementBuilder.BasicExpression activeBE = new SQLStatementBuilder.BasicExpression(activeField, SQLStatementBuilder.BasicOperator.EQUAL_OP, true);

        return new SQLStatementBuilder.BasicExpression(dateBE, SQLStatementBuilder.BasicOperator.AND_OP, activeBE);
    }

    @Scheduled(fixedRate=5000)
    public void scheduleTask(){
        try {
            List<String> columns = Arrays.asList(SubscriptionDao.ID,
                    SubscriptionDao.PRICE,
                    SubscriptionDao.ACTIVE,
                    SubscriptionDao.END_DATE,
                    SubscriptionDao.START_DATE,
                    SubscriptionDao.PLATF_ID,
                    SubscriptionDao.FREQUENCY,
                    SubscriptionDao.USER);

            Map<String, Object> key = new HashMap<String, Object>();
            key.put(SQLStatementBuilder.ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY,
                    filterByActiveAndDate(SubscriptionDao.END_DATE, SubscriptionDao.ACTIVE));

            EntityResult subscriptionsToUpdate = subscriptionService.subscriptionQueryAll(key, columns);

            int logER= subscriptionsToUpdate.calculateRecordNumber();

            for(int i=0;i<logER;i++){
                Map<String,Object> subsRegistry =  subscriptionsToUpdate.getRecordValues(i);

                Map<String,Object> attrs=new HashMap<>();
                attrs.put(SubscriptionDao.ACTIVE,false);
                attrs.put(SubscriptionDao.ID,subsRegistry.get(SubscriptionDao.ID));
                attrs.put(SubscriptionDao.PRICE,subsRegistry.get(SubscriptionDao.PRICE));
                attrs.put(SubscriptionDao.PLATF_ID,subsRegistry.get(SubscriptionDao.PLATF_ID));
                attrs.put(SubscriptionDao.FREQUENCY,subsRegistry.get(SubscriptionDao.FREQUENCY));
                attrs.put(SubscriptionDao.USER,subsRegistry.get(SubscriptionDao.USER));
                attrs.put(SubscriptionDao.START_DATE,subsRegistry.get(SubscriptionDao.START_DATE));

                Map<String,Object> keys=new HashMap<>();
                keys.put(SubscriptionDao.ID,subsRegistry.get(SubscriptionDao.ID));

                subscriptionService.subscriptionUpdate(attrs, keys);

                attrs.remove(SubscriptionDao.START_DATE);

                Date oldEndDate = (Date) subsRegistry.get(SubscriptionDao.END_DATE);
                attrs.put(SubscriptionDao.START_DATE, oldEndDate);

                attrs.remove(SubscriptionDao.ACTIVE);
                attrs.put(SubscriptionDao.ACTIVE,true);

                subscriptionService.subscriptionInsertAll(attrs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            EntityResult res = new EntityResultMapImpl();
            res.setCode(EntityResult.OPERATION_WRONG);
        }
    }
}
