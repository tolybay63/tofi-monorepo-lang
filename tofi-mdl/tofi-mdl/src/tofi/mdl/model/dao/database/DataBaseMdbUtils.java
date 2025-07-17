package tofi.mdl.model.dao.database;

import jandcode.commons.UtCnv;
import jandcode.commons.error.XError;
import jandcode.core.dbm.mdb.Mdb;
import jandcode.core.std.CfgService;
import jandcode.core.store.Store;
import jandcode.core.store.StoreRecord;
//import tofi.mdl.consts.FD_DataBaseType_consts;
import tofi.api.mdl.model.consts.FD_DataBaseType_consts;
import tofi.mdl.model.utils.EntityMdbUtils;
import tofi.mdl.model.utils.UtEntityTranslate;

import java.util.Map;

public class DataBaseMdbUtils extends EntityMdbUtils {
    Mdb mdb;
    String tableName;

    public DataBaseMdbUtils(Mdb mdb, String tableName) {
        super(mdb, tableName);
        this.mdb = mdb;
        this.tableName = tableName;
    }

    public String getIdMetaModel() {
        CfgService cfgSvc = mdb.getApp().bean(CfgService.class);
        return cfgSvc.getConf().getString("dbsource/default/id");
    }

    public Store load(Map<String, Object> params) throws Exception {
        Store st = mdb.createStore("DataBase.lang");
        String whe = "0=0 order by id";
        long id = UtCnv.toLong(params.get("id"));
        if (id > 0) {
            whe = "id="+id + " order by id";
        }
        mdb.loadQuery(st, "select * from DataBase where "+whe);
        //
        UtEntityTranslate ut = new UtEntityTranslate(mdb);
        String lang = UtCnv.toString(params.get("lang"));
        return ut.getTranslatedStore(st,"DataBase", lang);
    }

    public StoreRecord newRec() {
        Store st = mdb.createStore("DataBase.lang");
        return st.add();
    }

    public Store insert(Map<String, Object> rec) throws Exception {
        long id = insertEntity(rec);
        rec.put("id", id);
        return load(rec);
    }

    public Store update(Map<String, Object> rec) throws Exception {
        long id = UtCnv.toLong(rec.get("id"));
        if (id == 0) {
            throw new XError("Поле id должно иметь не нулевое значение");
        }
        updateEntity(rec);

        rec.put("id", id);
        return load(rec);
    }

    public void delete(Map<String, Object> rec) throws Exception {
        deleteEntity(rec);
    }

    public Store loadDbForSelect(String lang) throws Exception {
        Store st = mdb.createStore("DataBase.lang");
        mdb.loadQuery(st, "select * from DataBase where 0=0");

        UtEntityTranslate ut = new UtEntityTranslate(mdb);
        return ut.getTranslatedStore(st,"DataBase", lang);
    }



}
