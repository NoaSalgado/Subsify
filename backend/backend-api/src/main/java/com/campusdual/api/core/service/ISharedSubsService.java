package com.campusdual.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import java.util.List;
import java.util.Map;

public interface ISharedSubsService {
    EntityResult sharedSubsQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    EntityResult sharedSubsInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException;
    EntityResult sharedSubsUpdate(Map<String, Object> attributes, Map<String, Object> KeyValues) throws OntimizeJEERuntimeException;
    EntityResult sharedSubsDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException;
}
