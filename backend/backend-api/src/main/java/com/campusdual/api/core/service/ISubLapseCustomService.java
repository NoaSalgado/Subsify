package com.campusdual.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import java.util.List;
import java.util.Map;

public interface ISubLapseCustomService {

    EntityResult subLapseCustomQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    EntityResult subLapseCustomInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException;
    EntityResult subLapseCustomUpdate(Map<String, Object> attributes, Map<String, Object> KeyValues) throws OntimizeJEERuntimeException;
    EntityResult subLapseCustomDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException;
}
