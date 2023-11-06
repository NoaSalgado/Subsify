package com.campusdual.model.core.service;

import com.campusdual.api.core.service.IPlanService;
import com.campusdual.model.core.dao.PlanDao;
import com.campusdual.model.core.dao.PlanPriceDao;
import com.campusdual.model.core.dao.SubscriptionDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Lazy
@Service("PlanService")
public class PlanService implements IPlanService {

    @Autowired
    private PlanDao planDao;

    @Autowired
    private PlanPriceDao planPriceDao;

    @Autowired
    private PlanPriceService planPriceService;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;


    @Override
    public EntityResult planQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.planDao, keysValues, attributes, PlanDao.QUERY_PLAN_BY_PLATFORM);
    }


    @Override
    public EntityResult planByPlatformQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.planDao, keysValues, attributes, PlanDao.QUERY_PLAN_BY_PLATFORM);
    }

    @Override
        public EntityResult planActiveQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.planDao, keysValues, attributes, PlanDao.QUERY_ACTIVE_PLAN);
    }

    @Override
    public EntityResult singlePlanQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.planDao, keysValues, attributes, PlanDao.QUERY_SINGLE_PLAN);
    }

    @Override
    public EntityResult planInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        Map<String, Object> attrPlanInsert = new HashMap<>();
        attrPlanInsert.put(PlanDao.NAME, attributes.get(PlanDao.NAME));
        attrPlanInsert.put(PlanDao.PLATF_ID, attributes.get(PlanDao.PLATF_ID));
        attrPlanInsert.put(PlanDao.FR_ID, attributes.get(PlanDao.FR_ID));

        if(attributes.containsKey(PlanDao.CUSTOM)){
            attrPlanInsert.put(PlanDao.CUSTOM, attributes.get(PlanDao.CUSTOM));
        }

        EntityResult insertPlanER =  this.daoHelper.insert(this.planDao, attrPlanInsert);

        Map<String, Object> attrPlanPriceInsert = new HashMap<>();
        attrPlanPriceInsert.put(PlanPriceDao.VALUE, attributes.get(PlanPriceDao.VALUE));
        attrPlanPriceInsert.put(PlanPriceDao.START, attributes.get(PlanPriceDao.START));
        attrPlanPriceInsert.put(PlanPriceDao.PLAN_ID, insertPlanER.get(PlanDao.ID));

        EntityResult insertPlanPriceER =  this.planPriceService.planPriceInsert(attrPlanPriceInsert);

        return insertPlanER;
    }

    @Override
    public EntityResult planUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.planDao, attributes, keyValues);
    }

    @Override
    public EntityResult planDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.planDao, keyValues);
    }
}
