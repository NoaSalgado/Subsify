package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISubLapseCustomService;
import com.campusdual.model.core.dao.SubLapseCustomDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


public class SubLapseCustomService implements ISubLapseCustomService {

    @Autowired
    private SubLapseCustomDao subLapseCustomDao;

    @Autowired
    private SubLapseCustomService subLapseCustomService;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;


    @Override
    public EntityResult subLapseCustomQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.subLapseCustomDao, keysValues, attributes);
    }

    @Override
    public EntityResult subLapseCustomInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.insert(this.subLapseCustomDao, attributes);
    }

    @Override
    public EntityResult subLapseCustomUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.subLapseCustomDao, attributes, keyValues);
    }

    @Override
    public EntityResult subLapseCustomDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.subLapseCustomDao, keyValues);
    }
}
