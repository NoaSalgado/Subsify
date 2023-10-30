package com.campusdual.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import java.util.List;
import java.util.Map;

public interface ISharedSubService {
    EntityResult sharedSubQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    EntityResult sharedSubInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException;
    EntityResult sharedSubUpdate(Map<String, Object> attributes, Map<String, Object> KeyValues) throws OntimizeJEERuntimeException;
    EntityResult sharedSubDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException;
}
