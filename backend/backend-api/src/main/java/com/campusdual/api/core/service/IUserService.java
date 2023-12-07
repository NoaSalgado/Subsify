package com.campusdual.api.core.service;


import java.util.List;
import java.util.Map;

import com.ontimize.jee.common.dto.EntityResult;


public interface IUserService {
	public EntityResult userQuery(Map<?, ?> keyMap, List<?> attrList);
	public EntityResult userUpdate(Map<?, ?> attrMap, Map<?, ?> keyMap);
	public EntityResult userDelete(Map<?, ?> keyMap);
	public EntityResult userToShareQuery(Map<?, ?> keyMap, List<?> attrList);

}
