package com.campusdual.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import java.util.List;
import java.util.Map;

public interface IPlanPriceService {

    EntityResult planPriceQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;
    EntityResult planPriceInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException;
    EntityResult planPriceUpdate(Map<String, Object> attributes, Map<String, Object> KeyValues) throws OntimizeJEERuntimeException;
    EntityResult planPriceDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException;
    EntityResult freqByPlanPriceQuery(Map<String, Object> keyValues, List<String> attributes) throws OntimizeJEERuntimeException;
}
