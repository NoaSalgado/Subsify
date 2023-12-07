package com.campusdual.api.core.service;


import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import java.util.List;
import java.util.Map;

public interface ICategoryService {
        EntityResult categoryQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
        EntityResult categoryInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException;
        EntityResult categoryUpdate(Map<String, Object> attributes, Map<String, Object> KeyValues) throws OntimizeJEERuntimeException;
        EntityResult categoryDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException;
        EntityResult categoryActiveQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    }

