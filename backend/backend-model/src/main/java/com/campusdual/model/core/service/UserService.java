package com.campusdual.model.core.service;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.campusdual.model.core.dao.UserRoleDao;
import com.campusdual.model.core.dao.UserSubDao;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.security.PermissionsProviderSecured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.campusdual.api.core.service.IUserService;
import com.campusdual.model.core.dao.UserDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;


@Lazy
@Service("UserService")
public class UserService implements IUserService {

	@Autowired
	private UserDao userDao;
	@Autowired UserRoleDao userRoleDao;

	@Autowired
	private DefaultOntimizeDaoHelper daoHelper;

	public void loginQuery(Map<?, ?> key, List<?> attr) {
	}

	@Secured({ PermissionsProviderSecured.SECURED })
	public EntityResult userQuery(Map<?, ?> keyMap, List<?> attrList) {
		return this.daoHelper.query(userDao, keyMap, attrList);
	}

	@Secured({ PermissionsProviderSecured.SECURED })
	public EntityResult userUpdate(Map<?, ?> attrMap, Map<?, ?> keyMap) {
		return this.daoHelper.update(userDao, attrMap, keyMap);
	}

	@Secured({ PermissionsProviderSecured.SECURED })
	public EntityResult userDelete(Map<?, ?> keyMap) {
		return this.daoHelper.delete(this.userDao, keyMap);
	}

	@Override
	public EntityResult userToShareQuery(Map<?, ?> keyMap, List<?> attrList) {

	Map<String, Object> userToShareKV = new HashMap<>();
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		SQLStatementBuilder.BasicField field = new SQLStatementBuilder.BasicField(UserSubDao.USER);
		SQLStatementBuilder.BasicExpression bexp = new SQLStatementBuilder.BasicExpression(field, SQLStatementBuilder.BasicOperator.NOT_EQUAL_OP, username);
		SQLStatementBuilder.BasicField fieldAdmin =
				new SQLStatementBuilder.BasicField("ROLENAME");
		SQLStatementBuilder.BasicExpression adminBE =
				new SQLStatementBuilder.BasicExpression(fieldAdmin, SQLStatementBuilder.BasicOperator.NOT_EQUAL_OP, "admin");
		SQLStatementBuilder.BasicExpression  filterBE =
				new SQLStatementBuilder.BasicExpression(adminBE, SQLStatementBuilder.BasicOperator.AND_OP, bexp);
		userToShareKV.put(SQLStatementBuilder.ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY, filterBE);
	return this.daoHelper.query(this.userDao,userToShareKV,attrList,UserDao.USERS_TO_SHARE_QUERY);
	}

}
