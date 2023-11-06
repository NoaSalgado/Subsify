package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "PlanDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/PlanDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")

public class PlanDao extends OntimizeJdbcDaoSupport {

    public static final String ID = "PLAN_ID";
    public static final String NAME = "PLAN_NAME";
    public static final String PLATF_ID = "PLATF_ID";
    public static final String FR_ID = "FR_ID";
    public static final String CUSTOM = "IS_CUSTOM";
    public static final String QUERY_PLAN_BY_PLATFORM = "query_plan_by_platform";
    public static final String QUERY_ACTIVE_PLAN = "query_active_plan";
    public static final String QUERY_SINGLE_PLAN = "query_single_plan";
}
