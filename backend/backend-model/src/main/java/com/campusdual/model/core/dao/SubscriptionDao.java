package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "SubscriptionDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/SubscriptionDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class SubscriptionDao extends OntimizeJdbcDaoSupport {
    public static final String ATTR_SUBS_ID = "ID_SUBS";
    public static final String ATTR_PRICE = "PRICE";
    public static final String ATTR_FREQUENCY = "FREQUENCY";
    public static final String ATTR_ACTIVE = "ACTIVE";
    public static final String ATTR_START_DATE = "START_DATE";
    public static final String ATTR_END_DATE = "END_DATE";
    public static final String ATTR_PLATFORM = "PLATFORM";
    public static final String ATTR_USER = "USER";
}
