package com.campusdual.model.core.service;

import com.campusdual.api.core.service.IPlanPriceService;
import com.campusdual.model.core.dao.CategoryDao;
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
@Service("PlanPriceService")
public class PlanPriceService implements IPlanPriceService {


    @Autowired
    private PlanPriceDao planPriceDao;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;


    @Override
    public EntityResult planPriceQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.planPriceDao, keysValues, attributes);
    }

    @Override
    public EntityResult planPriceInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.insert(this.planPriceDao, attributes);
    }

    @Override
    public EntityResult planPriceUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.planPriceDao, attributes, keyValues);
    }

    @Override
    public EntityResult planPriceDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.planPriceDao, keyValues);
    }
}
