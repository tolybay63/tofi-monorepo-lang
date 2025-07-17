package tofi.mdl.model.dao.prop;

import jandcode.commons.UtCnv;
import jandcode.core.dbm.dao.BaseModelDao;
import jandcode.core.store.Store;
import jandcode.core.store.StoreRecord;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PropDao extends BaseModelDao {


    public Store loadPropTree(long propGr) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropTree(propGr);
    }


    public Store loadRec(long id) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadRec(id);
    }

    public StoreRecord newRec(long propGroup) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.newRec(propGroup);
    }

    public Store insert(Map<String, Object> params) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.insert(params);
    }

    public Store update(Map<String, Object> params) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.update(params);
    }

    public void delete(Map<String, Object> params) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        u.delete(UtCnv.toMap(params.get("rec")));
    }

    public boolean checkRefValue(long prop, String field) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.checkRefValue(prop, field);
    }

    public boolean checkStatus(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.checkStatus(prop);
    }

    public boolean checkProvider(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.checkProvider(prop);
    }

    public Store loadStatus(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "PropStatus");
        return u.loadStatus(prop);
    }

    public Store loadStatusForUpd(long prop, long factor) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "PropStatus");
        return u.loadStatusForUpd(prop, factor);
    }

    public String saveStatus(long prop, List<Map<String, Object>> params) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "PropStatus");
        return u.saveStatus(prop, params);
    }

    public Store loadProvider(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "PropProvider");
        return u.loadProvider(prop);
    }

    public Store loadProviderClsForSelect(long prop, long typ, String mode) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "PropProvider");
        return u.loadProviderClsForSelect(prop, typ, mode);
    }

    public Store loadProviderObjForSelect(long cls) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "PropProvider");
        return u.loadProviderObjForSelect(cls);
    }

    public StoreRecord saveProvider(Map<String, Object> rec, String mode) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "PropProvider");
        return u.saveProvider(rec, mode);
    }

    public void deleteProvider(long id) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "PropProvider");
        u.deleteProvider(id);
    }

    // PropVal
    public Store loadPropPeriodType(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropPeriodType(prop);
    }

    public Store loadPropPeriodTypeForUpd(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropPeriodTypeForUpd(prop);
    }

    public String savePropPeriodType(long prop, String metaModel, List<Map<String, Object>> params) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.savePropPeriodType(prop, metaModel, params);
    }

    public Store loadPropVal(long prop, String entity) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropVal(prop, entity);
    }

    public Store loadPropValForUpd(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropValForUpd(prop);
    }

    public void savePropRefVal(long prop, List<Map<String, Object>> params) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        u.savePropRefVal(prop, params);
    }

    public Store loadPropMeter(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropMeter(prop);
    }

    public Store loadPropMeterForUpd(Map<String, Object> params) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropMeterForUpd(params);
    }

    public Store loadPropMeterForUpdSave(Map<String, Object> params) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropMeterForUpdSave(params);
    }

    public void savePropMeter(Map<String, Object> params) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        u.savePropMeter(params);
    }

    public Store loadPropValEntity(long prop, long entityType, String lang) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropValEntity(prop, entityType, lang);
    }

    public Store loadPropValEntityForUpd(long prop, long entityType, String lang) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropValEntityForUpd(prop, entityType, lang);
    }

    public void savePropEntityVal(long prop, List<Map<String, Object>> lstData) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        u.savePropEntityVal(prop, lstData);
    }

    public Store loadAllPropForSelect() throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadAllPropForSelect();
    }

    public Store loadAllMultiPropForSelect() throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadAllMultiPropForSelect();
    }

    public Store loadPropForSelect(String propField) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropForSelect(propField);
    }

    public Set<Object> propPeriodType(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.propPeriodType(prop);
    }

    public Store loadPropValEntityForSelect(long prop, long entityType) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropValEntityForSelect(prop, entityType);
    }

    public Store loadPropValEntityTreePropForUpd(long prop, String lang) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropValEntityTreePropForUpd(prop, lang);
    }

    public Store loadPropComplex(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadPropComplex(prop);
    }

    public StoreRecord newRecComplex(Map<String, Object> rec) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.newRecComplex(rec);
    }

    public String getParentName(long propGr, long parent) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.getParentName(propGr, parent);
    }

    public Store loadItemsComplexProp(long prop) throws Exception {
        PropMdbUtils u = new PropMdbUtils(getMdb(), "Prop");
        return u.loadItemsComplexProp(prop);
    }

}
