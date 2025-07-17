package tofi.mdl.model.dao.measure;

import jandcode.commons.UtCnv;
import jandcode.core.auth.AuthService;
import jandcode.core.auth.AuthUser;
import jandcode.core.dbm.mdb.Mdb;
import jandcode.core.store.Store;
import jandcode.core.store.StoreIndex;
import jandcode.core.store.StoreRecord;
import tofi.api.mdl.model.consts.FD_PropType_consts;
import tofi.mdl.model.utils.EntityMdbUtils;
import tofi.mdl.model.utils.UtEntityTranslate;

import java.util.Map;

public class MeasureMdbUtils extends EntityMdbUtils {
    Mdb mdb;
    String tableName;

    public MeasureMdbUtils(Mdb mdb, String tableName) throws Exception {
        super(mdb, tableName);
        this.mdb = mdb;
        this.tableName = tableName;
    }

    public Store load(Map<String, Object> params) throws Exception {
        //**************** Используется при обращении из другого модуля
        long measureBase = UtCnv.toLong(params.get("measureBase"));
        long meter = UtCnv.toLong(params.get("meter"));
        String whe = "0=0";
        if (measureBase > 0) {
            whe = "id=" + measureBase + " or parent=" + measureBase;
        } else {
            if (meter > 0) {
                measureBase = mdb.loadQuery("select measure from Meter where id=:id ",
                        Map.of("id", meter)).get(0).getLong("measure");
                whe = "id=" + measureBase + " or parent=" + measureBase;
            }
        }
        //****************

        AuthService authSvc = mdb.getApp().bean(AuthService.class);
        AuthUser au = authSvc.getCurrentUser();
        long al = au.getAttrs().getLong("accesslevel");
        whe += " and accessLevel <= " + al;
        long id = UtCnv.toLong(params.get("id"));
        if (id > 0) {
            whe += " and id=" + id;
        }
        //
        Store st = mdb.createStore("Measure.lang");
        String sql = "select * from Measure where " + whe;
        mdb.loadQuery(st, sql);
        //
        UtEntityTranslate ut = new UtEntityTranslate(mdb);
        String lang = UtCnv.toString(params.get("lang"));
        return ut.getTranslatedStore(st,"Measure", lang);
    }

    public Store loadBase(Map<String, Object> params) throws Exception {
        AuthService authSvc = mdb.getApp().bean(AuthService.class);
        AuthUser au = authSvc.getCurrentUser();
        long al = au.getAttrs().getLong("accesslevel");
        Store st = mdb.createStore("Measure.lang");
        String sql = "select id, '' as name from Measure where parent is null and accessLevel <= " + al;
        mdb.loadQuery(st, sql);
        //
        UtEntityTranslate ut = new UtEntityTranslate(mdb);
        String lang = UtCnv.toString(params.get("lang"));
        return ut.getTranslatedStore(st,"Measure", lang);
        //return st;
    }

    protected Store loadRec(long id) throws Exception {
        Store st = mdb.createStore("Measure");
        return mdb.loadQuery(st, """
                    select * from Measure where id=:id
                """, Map.of("id", id));
    }

    public Store insert(Map<String, Object> params) throws Exception {
        Map<String, Object> rec = UtCnv.toMap(params.get("rec"));
        //
        long id = insertEntity(rec);

        //add to PropVal
        //todo Temp!!!

/*
        Store rTmp = mdb.loadQuery("select id, allItem from Prop where measure=:m and proptype=:pt",
                Map.of("m", id, "pt", FD_PropType_consts.measure));
        if (rTmp.size() > 0) {
            if (rTmp.get(0).getBoolean("allItem")) {
                long prop = rTmp.get(0).getLong("id");
                mdb.insertRec("PropVal", Map.of("prop", prop, "measure", id), true);
            }
        }
*/

        //
        rec.put("id", id);
        return load(rec);
        //
    }

    public Store update(Map<String, Object> params) throws Exception {
        Map<String, Object> rec = UtCnv.toMap(params.get("rec"));
        //
        updateEntity(rec);
        //
        //rec.put("lang", UtCnv.toString(params.get("lang")));
        return load(rec);
    }

    public void delete(Map<String, Object> rec) throws Exception {
        deleteEntity(rec);
    }


}
