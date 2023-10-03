package com.campusdual.ws.core.rest;

import com.campusdual.api.core.service.ISubscriptionService;
import com.campusdual.model.core.dao.SubscriptionDao;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.server.rest.ORestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionRestController extends ORestController<ISubscriptionService> {

    @Autowired
    private ISubscriptionService subscriptionService;
    @Override
    public ISubscriptionService getService() {
        return this.subscriptionService;
    }

    @RequestMapping(value = "TODO", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResult currentOffersSearch(@RequestBody Map<String, Object> req) {
        try {
            List<String> columns = (List<String>) req.get("columns");
            Map<String, Object> key = new HashMap<String, Object>();
            //key.put(SQLStatementBuilder.ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY,
              //      searchBetween(OfferDao.ATTR_START_DATE));
            System.out.println("TODO");

            /*
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
            }*/

            return subscriptionService.subscriptionDelete(null);
        } catch (Exception e) {
            e.printStackTrace();
            ///EntityResult res = new EntityResultMapImpl();
            //res.setCode(EntityResult.OPERATION_WRONG);
            //return res;
            return null;
        }
    }
}
