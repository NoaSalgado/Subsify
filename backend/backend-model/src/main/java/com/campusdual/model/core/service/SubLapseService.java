package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISubLapseService;
import com.campusdual.model.core.dao.SubLapseDao;
import com.campusdual.model.core.dao.SubscriptionDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service("SubLapseService")
public class SubLapseService implements ISubLapseService {
    @Autowired
    private SubLapseDao subscriptionDao;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;
    @Override
    public EntityResult subLapseQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return null;
    }

    @Override
    public EntityResult subLapseInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return null;
    }

    @Override
    public EntityResult subLapseUpdate(Map<String, Object> attributes, Map<String, Object> KeyValues) throws OntimizeJEERuntimeException {
        return null;
    }

    @Override
    public EntityResult subLapseDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return null;
    }
}
