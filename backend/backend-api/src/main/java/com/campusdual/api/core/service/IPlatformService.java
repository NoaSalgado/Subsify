package com.campusdual.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import java.util.List;
import java.util.Map;

public interface IPlatformService {
    EntityResult platformQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;

    EntityResult platformAdminQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;

    EntityResult platformInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException;
    EntityResult platformUpdate(Map<String, Object> attributes, Map<String, Object> KeyValues) throws OntimizeJEERuntimeException;
    EntityResult platformDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException;
}