package com.campusdual.model.core.jobs;


import com.campusdual.model.core.dao.SubscriptionDao;
import com.campusdual.model.core.service.SubscriptionService;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
            /*
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken("demo", "demouser");
            Authentication auth = authManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            */
            List<String> columns = Arrays.asList(SubscriptionDao.ID,
                    SubscriptionDao.PRICE,
                    SubscriptionDao.ATTR_SUBS_ACTIVE,
                    SubscriptionDao.ATTR_SUBS_END_DATE,
                    SubscriptionDao.ATTR_SUBS_START_DATE,
                    SubscriptionDao.ATTR_SUBS_PLATFORM_ID,
                    SubscriptionDao.FREQUENCY,
                    SubscriptionDao.ATTR_SUBS_USER);

            Map<String, Object> key = new HashMap<String, Object>();
            key.put(SQLStatementBuilder.ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY,
                    filterByActiveAndDate(SubscriptionDao.ATTR_SUBS_END_DATE, SubscriptionDao.ATTR_SUBS_ACTIVE));

            EntityResult subscriptionsToUpdate = subscriptionService.subscriptionQueryAll(key, columns);

            int logER= subscriptionsToUpdate.calculateRecordNumber();

            for(int i=0;i<logER;i++){
                Map<String,Object> subsRegistry =  subscriptionsToUpdate.getRecordValues(i);

                Map<String,Object> attrs=new HashMap<>();
                attrs.put(SubscriptionDao.ATTR_SUBS_ACTIVE,false);
                attrs.put(SubscriptionDao.ID,subsRegistry.get(SubscriptionDao.ID));
                attrs.put(SubscriptionDao.PRICE,subsRegistry.get(SubscriptionDao.PRICE));
                attrs.put(SubscriptionDao.ATTR_SUBS_PLATFORM_ID,subsRegistry.get(SubscriptionDao.ATTR_SUBS_PLATFORM_ID));
                attrs.put(SubscriptionDao.FREQUENCY,subsRegistry.get(SubscriptionDao.FREQUENCY));
                attrs.put(SubscriptionDao.ATTR_SUBS_USER,subsRegistry.get(SubscriptionDao.ATTR_SUBS_USER));
                attrs.put(SubscriptionDao.ATTR_SUBS_START_DATE,subsRegistry.get(SubscriptionDao.ATTR_SUBS_START_DATE));

                Map<String,Object> keys=new HashMap<>();
                keys.put(SubscriptionDao.ID,subsRegistry.get(SubscriptionDao.ID));

                subscriptionService.subscriptionUpdate(attrs, keys);

                //Date oldStartDate = (Date) attrs.get(SubscriptionDao.ATTR_SUBS_START_DATE);
                attrs.remove(SubscriptionDao.ATTR_SUBS_START_DATE);

                Date oldEndDate = (Date) subsRegistry.get(SubscriptionDao.ATTR_SUBS_END_DATE);
                attrs.put(SubscriptionDao.ATTR_SUBS_START_DATE, oldEndDate);

                attrs.remove(SubscriptionDao.ATTR_SUBS_ACTIVE);
                attrs.put(SubscriptionDao.ATTR_SUBS_ACTIVE,true);

                subscriptionService.subscriptionInsertAll(attrs);
            }
            System.out.println("hola");

        } catch (Exception e) {
            e.printStackTrace();
            EntityResult res = new EntityResultMapImpl();
            res.setCode(EntityResult.OPERATION_WRONG);
        }
    }
}
