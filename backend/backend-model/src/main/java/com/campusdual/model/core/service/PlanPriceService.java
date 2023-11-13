package com.campusdual.model.core.service;

import com.campusdual.api.core.service.IPlanPriceService;
import com.campusdual.model.core.dao.CategoryDao;
import com.campusdual.model.core.dao.PlanDao;
import com.campusdual.model.core.dao.PlanPriceDao;
import com.campusdual.model.core.dao.SubLapseDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Lazy
@Service("PlanPriceService")
public class PlanPriceService implements IPlanPriceService {


    @Autowired
    private PlanPriceDao planPriceDao;

    @Autowired
    private PlanService planService;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;


    @Override
    public EntityResult planPriceQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.planPriceDao, keysValues, attributes, PlanPriceDao.ORDER_BY_END_DATE_QUERY);
    }

    @Override
    public EntityResult planPriceInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        Map<String, Object> newKeyValues = new HashMap<>();
        newKeyValues.put(PlanPriceDao.PLAN_ID,attributes.get(PlanPriceDao.PLAN_ID));

        List<String> newAttributes = new ArrayList<>();
        newAttributes.add(PlanPriceDao.ID);
        newAttributes.add(PlanPriceDao.START);
        newAttributes.add(PlanPriceDao.END);

        EntityResult er = this.planService.planQuery(newKeyValues,newAttributes);
        int erSize = er.calculateRecordNumber();
        ZoneId zoneId = ZoneId.systemDefault();

        for(int i=0;i<erSize;i++){

            Map<String,Object> planPriceRegistry =  er.getRecordValues(i);
            java.sql.Date oldEndDate = (java.sql.Date) planPriceRegistry.get(PlanPriceDao.END);
            java.sql.Date oldStartDate = (java.sql.Date) planPriceRegistry.get(PlanPriceDao.START);
            LocalDate oldStartDateLD = oldStartDate.toLocalDate();

            if(oldEndDate == null) {
                java.util.Date newEndDate = (java.util.Date) attributes.get(PlanPriceDao.START);
                Instant instant = newEndDate.toInstant();
                LocalDate newEndDateLD = instant.atZone(zoneId).toLocalDate();

                if (newEndDateLD.isBefore(oldStartDateLD) || newEndDateLD.isEqual(oldStartDateLD)){
                    EntityResult errorEr = new EntityResultMapImpl();
                    errorEr.setCode(EntityResult.OPERATION_WRONG);
                    errorEr.setMessage("ERROR_PLAN_INSERT_DATE_MESSAGE");
                    return errorEr;
                }
                newEndDateLD = newEndDateLD.minusDays(1);
                Map<String, Object> newKeyValuesUpdate = new HashMap<>();
                newKeyValuesUpdate.put(PlanPriceDao.ID, planPriceRegistry.get(PlanPriceDao.ID));

                Map<String, Object> newAttributesUpdate = new HashMap<>();
                Date newDate = Date.from((newEndDateLD.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                newAttributesUpdate.put(PlanPriceDao.END, newDate);

                this.planPriceDao.update(newAttributesUpdate, newKeyValuesUpdate);
            }

            /*Date newDate = Date.from((newDateLD.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            attrs.put(SubLapseDao.START, newDate);

            subLapseService.subLapseInsert(attrs);*/
        }


        return this.daoHelper.insert(this.planPriceDao,attributes);
    }

    @Override
    public EntityResult planPriceUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.planPriceDao, attributes, keyValues);
    }

    @Override
    public EntityResult planPriceDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.planPriceDao, keyValues);
    }

    @Override
    public EntityResult freqByPlanPriceQuery(Map<String, Object> keyValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.planPriceDao, keyValues, attributes, PlanPriceDao.FREQ_BY_PLAN_QUERY);
    }

    @Override
    public EntityResult planPriceActiveQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Map<String,Object> planActiveKV=new HashMap<>();
        planActiveKV.put(PlanPriceDao.PLAN_ID,attributes.get(Integer.parseInt(PlanPriceDao.PLAN_ID)));
        planActiveKV.put(PlanPriceDao.END,null);
        return this.daoHelper.query(this.planPriceDao, planActiveKV, attributes);
    }
}
