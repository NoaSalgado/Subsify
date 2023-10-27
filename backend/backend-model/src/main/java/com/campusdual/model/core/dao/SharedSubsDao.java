package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "SharedSubsDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/SharedSubsDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class SharedSubsDao extends OntimizeJdbcDaoSupport {
    public static final String ID = "SHARED_SUBS_ID";
    public static final String USER = "SHARED_SUBS_USER";
    public static final String SUBS_ID = "SUBS_ID";
}
