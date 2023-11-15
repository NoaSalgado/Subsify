package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "PlatformDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/PlatformDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class PlatformDao extends OntimizeJdbcDaoSupport {

    public static final String ID = "PLATF_ID";
    public static final String NAME = "PLATF_NAME";
    public static final String CAT_ID = "CAT_ID";
    public static final String PLATF_ACTIVE = "PLATF_ACTIVE";
    public static final String PLATF_LINK = "PLATF_LINK";
    public static final String PLATF_IMAGE = "PLATF_IMAGE";
    public static final String CUSTOM = "PLATF_IS_CUSTOM";
    public static final String DEFAULT_QUERY = "query_default";
    public static final String USERS_BY_CUSTOM_PLAT_QUERY = "users_by_custom_plat";

}