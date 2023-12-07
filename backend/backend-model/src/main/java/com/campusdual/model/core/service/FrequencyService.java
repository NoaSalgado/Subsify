package com.campusdual.model.core.service;

import com.campusdual.api.core.service.IFrequencyService;
import com.campusdual.model.core.dao.FrequencyDao;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service("FrequencyService")
public class FrequencyService implements IFrequencyService {
    @Autowired
    private FrequencyDao frequencyDao;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    @Override
    public EntityResult frequencyQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.frequencyDao, keysValues, attributes);
    }

    @Override
    public EntityResult frequencyInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.insert(this.frequencyDao, attributes);
    }

    @Override
    public EntityResult frequencyUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.frequencyDao, attributes, keyValues);
    }

    @Override
    public EntityResult frequencyDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.frequencyDao, keyValues);
    }
}
