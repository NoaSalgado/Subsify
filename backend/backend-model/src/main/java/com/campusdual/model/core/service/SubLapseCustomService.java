package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISubLapseCustomService;
import com.campusdual.model.core.dao.SubLapseCustomDao;
import com.campusdual.model.core.dao.SubLapseDao;
import com.campusdual.model.core.dao.SubscriptionDao;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Lazy
@Service("SubLapseCustom")
public class SubLapseCustomService implements ISubLapseCustomService {

    @Autowired
    private SubLapseCustomDao subLapseCustomDao;

    @Autowired
    private SubLapseCustomService subLapseCustomService;
    @Autowired
    private SubLapseService subLapseService;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;


    @Override
    public EntityResult subLapseCustomQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.subLapseCustomDao, keysValues, attributes);
    }

    @Override
    public EntityResult subLapseCustomInsert(Map<String, Object> attributes) throws OntimizeJEERuntimeException {
        Map<String, Object> attrInsert = new HashMap<>();
        if (attributes.containsKey(SubLapseDao.ID)) {
            if(attributes.containsKey(SubLapseCustomDao.SLC_END)){
                Date sublapseCustomEnd = (Date) attributes.get(SubLapseCustomDao.SLC_END);
                Date sublapseCustomStart = (Date) attributes.get(SubLapseCustomDao.SLC_START);
                if(sublapseCustomEnd.before(sublapseCustomStart)){
                    EntityResult errorEr = new EntityResultMapImpl();
                    errorEr.setCode(EntityResult.OPERATION_WRONG);
                    errorEr.setMessage("WRONG_DATE");
                    return errorEr;
                }
            }
            Map<String, Object> subLapseKV = new HashMap<>();
            int subLapseId = Integer.parseInt((String) attributes.get(SubLapseDao.ID));
            subLapseKV.put(SubLapseDao.ID, subLapseId);

            EntityResult subLapseER = this.subLapseService.subLapseQuery(
                    subLapseKV,
                    List.of(SubscriptionDao.ID));
            attrInsert.put(SubLapseCustomDao.SUBS_ID,subLapseER.getRecordValues(0).get(SubscriptionDao.ID));
            attrInsert.put(SubLapseCustomDao.SLC_START,attributes.get(SubLapseCustomDao.SLC_START));
            attrInsert.put(SubLapseCustomDao.SLC_PRICE,attributes.get(SubLapseCustomDao.SLC_PRICE));
            if(attributes.containsKey(SubLapseCustomDao.SLC_END)){
                attrInsert.put(SubLapseCustomDao.SLC_END, attributes.get(SubLapseCustomDao.SLC_END));
            }
        } else {
            attrInsert = attributes;
        }
      return this.daoHelper.insert(this.subLapseCustomDao, attrInsert);
    }

    @Override
    public EntityResult subLapseCustomUpdate(Map<String, Object> attributes, Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.update(this.subLapseCustomDao, attributes, keyValues);
    }

    @Override
    public EntityResult subLapseCustomDelete(Map<String, Object> keyValues) throws OntimizeJEERuntimeException {
        return this.daoHelper.delete(this.subLapseCustomDao, keyValues);
    }

    @Override
    public EntityResult subLapseCustomBySubIdQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException {
        return this.daoHelper.query(this.subLapseCustomDao, keysValues, attributes, SubLapseCustomDao.SCL_CUSTOM_SUB_LAPSE_QUERY);
    }

    public EntityResult customPriceQuery(Map<String, Object> keysValues, List<String> attributes) throws OntimizeJEERuntimeException{

        Map<String,Object>priceKV=new HashMap<>();
        SQLStatementBuilder.BasicField subLapseEndBF=new SQLStatementBuilder.BasicField(SubLapseCustomDao.SLC_START);
        SQLStatementBuilder.BasicExpression subLapseBE=new SQLStatementBuilder.BasicExpression(
                subLapseEndBF, SQLStatementBuilder.BasicOperator.LESS_EQUAL_OP,keysValues.get(SubLapseDao.END));
        priceKV.put(SQLStatementBuilder.ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY,subLapseBE);
        priceKV.put(SubscriptionDao.ID,keysValues.get(SubscriptionDao.ID));

        return this.daoHelper.query(this.subLapseCustomDao,  priceKV, attributes, SubLapseCustomDao.SCL_CUSTOM_PRICE_QUERY);
    }
}
