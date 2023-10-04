package com.campusdual.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import java.util.List;
import java.util.Map;

public interface IFrequencyService {
    EntityResult frequencyQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    EntityResult frequencyInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException;
    EntityResult frequencyUpdate(Map<String, Object> attributes, Map<String, Object> KeyValues) throws OntimizeJEERuntimeException;
    EntityResult frequencyDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException;
}
