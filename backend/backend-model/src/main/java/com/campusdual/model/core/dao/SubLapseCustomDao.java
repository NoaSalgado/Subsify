package com.campusdual.model.core.dao;


import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "SubLapseCustomDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/SubLapseCustomDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class SubLapseCustomDao extends OntimizeJdbcDaoSupport {

    public static final String ID = "SLC_ID";
    public static final String SUBS_ID = "SUBS_ID";
    public static final String SLC_PRICE = "SLC_PRICE";
    public static final String SLC_START = "SLC_START";
    public static final String SLC_END = "SLC_END";
}
