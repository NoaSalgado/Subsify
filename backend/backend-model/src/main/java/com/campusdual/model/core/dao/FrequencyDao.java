package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "FrequencyDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/FrequencyDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class FrequencyDao extends OntimizeJdbcDaoSupport {

    public static final String ID = "FR_ID";
    public static final String NAME = "FR_NAME";
    public static final String VALUE = "FR_VALUE";
}
