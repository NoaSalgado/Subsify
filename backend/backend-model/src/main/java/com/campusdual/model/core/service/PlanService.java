package com.campusdual.model.core.service;

import com.campusdual.api.core.service.IPlanService;
import com.campusdual.model.core.dao.PlanDao;
import com.campusdual.model.core.dao.PlanPriceDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Lazy
@Service("PlanService")
public class PlanService implements IPlanService {

    @Autowired
    private PlanDao planDao;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;


    @Override
    public EntityResult planQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.planDao, keysValues, attributes);
    }

    @Override
    public EntityResult planByPlatformQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.planDao, keysValues, attributes, PlanDao.QUERY_PLAN_BY_PLATFORM);
    }

    @Override
    public EntityResult planInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.insert(this.planDao, attributes);
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
