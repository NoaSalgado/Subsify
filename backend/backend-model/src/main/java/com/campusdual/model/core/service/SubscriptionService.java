package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISubscriptionService;
import com.campusdual.model.core.dao.SubscriptionDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Lazy
@Service("SubscriptionService")
public class SubscriptionService implements ISubscriptionService {

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    @Override
    public EntityResult subscriptionQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Map<String, Object> newKeyValues = new HashMap<>(keysValues);
        newKeyValues.put("USER_",username);
        return this.daoHelper.query(this.subscriptionDao, newKeyValues, attributes);
    }

    @Override
    public EntityResult subscriptionInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.insert(this.subscriptionDao, attributes);
    }

    @Override
    public EntityResult subscriptionUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.subscriptionDao, attributes, keyValues);
    }

    @Override
    public EntityResult subscriptionDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.subscriptionDao, keyValues);
    }
}
