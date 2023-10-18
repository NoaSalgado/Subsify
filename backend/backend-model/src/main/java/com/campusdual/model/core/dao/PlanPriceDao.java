package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "PlanPriceDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/PlanPriceDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")

public class PlanPriceDao extends OntimizeJdbcDaoSupport {


    public static final String ID = "PLAN_PRICE_ID";
    public static final String VALUE = "PLAN_PRICE_VALUE";
    public static final String START = "PLAN_PRICE_START";
    public static final String END = "PLAN_PRICE_END";
    public static final String PLAN_ID="PLAN_ID";


}
