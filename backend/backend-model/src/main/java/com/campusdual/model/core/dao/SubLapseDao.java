package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;


@Repository(value = "SubLapseDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/SubLapseDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class SubLapseDao extends OntimizeJdbcDaoSupport {

    public static final String ID = "SUB_LAPSE_ID";
    public static final String START = "SUB_LAPSE_START";
    public static final String END = "SUB_LAPSE_END";
    public static final String PRICE = "SUB_LAPSE_PRICE";
    public static final String SUBS_ID = "SUBS_ID";
    public static final String ACTIVE_QUERY = "query_active";
    public static final String RENEWAL_QUERY = "query_renewal";
    public static final String CHARTCATEGORY_QUERY = "query_chart_category";
}
