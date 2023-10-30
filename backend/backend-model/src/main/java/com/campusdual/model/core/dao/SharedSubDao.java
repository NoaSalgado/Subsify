package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "SharedSubDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/SharedSubDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class SharedSubDao extends OntimizeJdbcDaoSupport {
    public static final String ID = "SHARED_SUB_ID";
    public static final String USER = "SHARED_SUB_USER";
    public static final String SUBS_ID = "SUBS_ID";
}
