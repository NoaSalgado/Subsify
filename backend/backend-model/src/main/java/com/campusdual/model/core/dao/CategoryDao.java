package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "CategoryDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/CategoryDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class CategoryDao extends OntimizeJdbcDaoSupport {

    public static final String ID = "CAT_ID";
    public static final String NAME = "CAT_NAME";
    public static final String ACTIVE = "CAT_ACTIVE";
    public static final String CUSTOM = "IS_CUSTOM";
    public static final String ACTIVE_QUERY = "query_active";
}