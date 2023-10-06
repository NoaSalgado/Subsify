package com.campusdual.model.core.jobs;


import com.campusdual.model.core.dao.FrequencyDao;
import com.campusdual.model.core.dao.SubLapseDao;
import com.campusdual.model.core.dao.SubscriptionDao;
import com.campusdual.model.core.service.SubLapseService;
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
    SubLapseService subLapseService;


    /*
    private SQLStatementBuilder.BasicExpression filterByActiveAndDate(String endDate, String active) {
        SQLStatementBuilder.BasicField endDateField = new SQLStatementBuilder.BasicField(endDate);
        SQLStatementBuilder.BasicExpression dateBE = new SQLStatementBuilder.BasicExpression(endDateField, SQLStatementBuilder.BasicOperator.LESS_OP, LocalDate.now());

        SQLStatementBuilder.BasicField activeField = new SQLStatementBuilder.BasicField(active);
        SQLStatementBuilder.BasicExpression activeBE = new SQLStatementBuilder.BasicExpression(activeField, SQLStatementBuilder.BasicOperator.EQUAL_OP, true);

        return new SQLStatementBuilder.BasicExpression(dateBE, SQLStatementBuilder.BasicOperator.AND_OP, activeBE);
    }*/

    @Scheduled(fixedRate=5000)
    public void scheduleTask(){
        try {

            List<String> columns = Arrays.asList(
                    SubLapseDao.ID,
                    SubLapseDao.END,
                    SubLapseDao.START,
                    SubLapseDao.SUBS_ID,
                    SubLapseDao.PRICE,
                    SubscriptionDao.FREQUENCY);

            Map<String, Object> key = new HashMap<String, Object>();
            EntityResult subscriptionsToUpdate = subLapseService.subLapseQueryRenewal(null, columns);

            int logER= subscriptionsToUpdate.calculateRecordNumber();

            for(int i=0;i<logER;i++){

                Map<String,Object> subsRegistry =  subscriptionsToUpdate.getRecordValues(i);

                Map<String,Object> attrs=new HashMap<>();
                attrs.put(SubLapseDao.SUBS_ID, subsRegistry.get(SubLapseDao.SUBS_ID));
                attrs.put(SubLapseDao.PRICE, subsRegistry.get(SubLapseDao.PRICE));
                attrs.put(SubscriptionDao.FREQUENCY, subsRegistry.get(SubscriptionDao.FREQUENCY));

                java.sql.Date oldEndDate = (java.sql.Date) subsRegistry.get(SubLapseDao.END);
                LocalDate dateLD = oldEndDate.toLocalDate();
                LocalDate newDateLD = dateLD.plusDays(1);

                attrs.put(SubLapseDao.START, newDateLD);

                subLapseService.subLapseInsertRenew(attrs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            EntityResult res = new EntityResultMapImpl();
            res.setCode(EntityResult.OPERATION_WRONG);
        }
    }
}
