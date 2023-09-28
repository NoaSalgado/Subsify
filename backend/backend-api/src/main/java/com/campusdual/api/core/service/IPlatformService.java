package com.campusdual.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import java.util.List;
import java.util.Map;

public interface IPlatformService {
    EntityResult platformQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException;

}