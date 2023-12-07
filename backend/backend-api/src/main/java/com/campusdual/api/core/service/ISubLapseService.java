package com.campusdual.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import java.util.List;
import java.util.Map;

public interface ISubLapseService {
    EntityResult subLapseQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    EntityResult subLapseCatQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    EntityResult subLapseInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException;
    EntityResult subLapseUpdate(Map<String, Object> attributes, Map<String, Object> KeyValues) throws OntimizeJEERuntimeException;
    EntityResult subLapseDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException;
    EntityResult subLapseToRenewQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    EntityResult subLapseQueryRenewal(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    public EntityResult subLapseChartCategoryQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
}