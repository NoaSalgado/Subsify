package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "SubscriptionDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/SubscriptionDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class SubscriptionDao extends OntimizeJdbcDaoSupport {
    public static final String ATTR_SUBS_ID = "SUBS_ID";
    public static final String ATTR_PRICE = "SUBS_PRICE";
    public static final String ATTR_FREQUENCY = "SUBS_FREQUENCY";
    public static final String ATTR_ACTIVE = "SUBS_ACTIVE";
    public static final String ATTR_START_DATE = "SUBS_START_DATE";
    public static final String ATTR_END_DATE = "SUBS_END_DATE";
    public static final String ATTR_PLATFORM_ID = "PLATF_ID";
    public static final String ATTR_USER = "USER_";
    public static final String ATTR_PLATFORM_NAME = "PLATF_NAME";
}
