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
    public static final String DEFAULT_QUERY = "query_default";
}