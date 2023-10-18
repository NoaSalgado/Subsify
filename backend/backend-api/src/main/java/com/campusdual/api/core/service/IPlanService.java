package com.campusdual.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import java.util.List;
import java.util.Map;

public interface IPlanService {

    EntityResult planQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    EntityResult planInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException;
    EntityResult planUpdate(Map<String, Object> attributes, Map<String, Object> KeyValues) throws OntimizeJEERuntimeException;
    EntityResult planDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException;
}
