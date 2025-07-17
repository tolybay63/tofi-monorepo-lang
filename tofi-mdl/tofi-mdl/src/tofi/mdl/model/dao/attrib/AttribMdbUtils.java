package tofi.mdl.model.dao.attrib;

import jandcode.commons.UtCnv;
import jandcode.commons.error.XError;
import jandcode.core.auth.AuthService;
import jandcode.core.auth.AuthUser;
import jandcode.core.dbm.mdb.Mdb;
import jandcode.core.store.Store;
import tofi.mdl.model.utils.EntityMdbUtils;
import tofi.mdl.model.utils.UtEntityTranslate;

import java.util.Map;

public class AttribMdbUtils extends EntityMdbUtils {
    Mdb mdb;
    String tableName;

    public AttribMdbUtils(Mdb mdb, String tableName) {
        super(mdb, tableName);
        this.mdb = mdb;
        this.tableName = tableName;
    }

    /**
     * Загрузка Attrib с пагинацией
     *
     * @param params Map
     * @return Map
     */
    public Store load(Map<String, Object> params) throws Exception {
        AuthService authSvc = mdb.getApp().bean(AuthService.class);
        AuthUser au = authSvc.getCurrentUser();
        long al = au.getAttrs().getLong("accesslevel");
        String whe = " accessLevel <= " + al;
        long id = UtCnv.toLong(params.get("id"));
        if (id > 0) {
            whe += " and id=" + id;
        }
        //
        Store st = mdb.createStore("Attrib.lang");
        String sql = "select * from Attrib where " + whe;
        sql += " order by id";

        mdb.loadQuery(st, sql);

/*todo Future for resolveDicts

        for (StoreField fld : st.getFields()) {
            if (((DefaultStoreField) fld).getDict() != null) {
                String nameDict = fld.getDict();
                String mameFld = fld.getName();
                // ...
            }
        }
*/

        //
        UtEntityTranslate ut = new UtEntityTranslate(mdb);
        String lang = UtCnv.toString(params.get("lang"));
        return ut.getTranslatedStore(st,"Attrib", lang);
        //


    }


    /**
     * Delete Attrib
     *
     * @param rec Map
     */

    public void delete(Map<String, Object> rec) throws Exception {
        deleteEntity(rec);
    }

    /**
     * Update Factor & FactorVal
     *
     * @param params Map
     * @return Store
     */
    public Store update(Map<String, Object> params) throws Exception {
        Map<String, Object> rec = (UtCnv.toMap(params.get("rec")));

        long id = UtCnv.toLong(rec.get("id"));
        if (id == 0) {
            throw new XError("Поле id должно иметь не нулевое значение");
        }
        //
        updateEntity(rec);
        //
        // Загрузка записи
        return load(rec);
    }

    /**
     * Insert Attrib
     *
     * @param params Map
     * @return Store
     */
    public Store insert(Map<String, Object> params) throws Exception {
        Map<String, Object> rec = UtCnv.toMap(params.get("rec"));
        //
        long id = insertEntity(rec);
        //
        rec.put("id", id);
        return load(rec);
    }

/*
    public StoreRecord loadRec(Map<String, Object> params) throws Exception {
        long id = UtCnv.toLong(params.get("id"));
        StoreRecord st = mdb.createStoreRecord("Attrib");
        mdb.loadQueryRecord(st, "select * from Attrib where id=:id", Map.of("id", id));
        return st;
    }
*/


    public Store loadAttribChar(Map<String, Object> params) throws Exception {
        Store st = mdb.createStore("AttribChar");
        mdb.loadQuery(st, "select * from AttribChar where attrib=:attrib", params);
        //mdb.resolveDicts(st);
        return st;
    }

    public Store insertAttribChar(Map<String, Object> params) throws Exception {
        Map<String, Object> rec = UtCnv.toMap(params.get("rec"));
        long id = mdb.insertRec("AttribChar", rec);
        //
        Store st = mdb.createStore("AttribChar");
        mdb.loadQuery(st, "select * from AttribChar where id=:id", Map.of("id", id));

        return st;
    }

    public Store updateAttribChar(Map<String, Object> params) throws Exception {
        Map<String, Object> rec = (UtCnv.toMap(params.get("rec")));
        long id = UtCnv.toLong(rec.get("id"));
        if (id == 0) {
            throw new XError("Поле id должно иметь не нулевое значение");
        }
        //
        mdb.updateRec("AttribChar", rec);
        //
        // Загрузка записи
        Store st = mdb.createStore("AttribChar");

        mdb.loadQuery(st, "select * from AttribChar where id=:id", Map.of("id", id));
        //mdb.resolveDicts(st);

        return st;
    }

    public void deleteAttribChar(Map<String, Object> rec) throws Exception {
        long id = UtCnv.toLong(rec.get("id"));
        mdb.deleteRec("AttribChar", id);
    }


    public Store loadForSelect() throws Exception {
        Store st = mdb.createStore("Attrib");
        return mdb.loadQuery(st, "select * from Attrib where 0=0");
    }


}
