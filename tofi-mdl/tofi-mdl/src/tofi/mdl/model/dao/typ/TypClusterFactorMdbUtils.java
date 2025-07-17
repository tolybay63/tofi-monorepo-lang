package tofi.mdl.model.dao.typ;

import jandcode.commons.UtCnv;
import jandcode.core.dbm.mdb.Mdb;
import jandcode.core.store.Store;
import jandcode.core.store.StoreRecord;
import tofi.mdl.model.dao.factor.FactorDao;
import tofi.mdl.model.utils.UtEntityTranslate;
import java.util.Map;

public class TypClusterFactorMdbUtils {
    Mdb mdb;

    public TypClusterFactorMdbUtils(Mdb mdb) {
        this.mdb = mdb;
    }

    public Store loadTypClusterFactor(long id, long typ, String lang) throws Exception {
        Store st = mdb.createStore("TypClusterFactor.full");
        TypDao typDao = mdb.createDao(TypDao.class);
        long typParent = typDao.loadRec(Map.of("id", typ, "lang", lang)).get(0).getLong("parent");
        //Перевод Factor
        Store stFactor = mdb.createStore("Factor.lang");
        mdb.loadQuery(stFactor, """
            select *
            from Factor f
                left join TableLang l on l.nameTable='Factor' and l.idTable=f.id and l.lang=:lang
            where f.id in (select factor from TypClusterFactor)
            union all
            select *
            from Factor f
                left join TableLang l on l.nameTable='Factor' and l.idTable=f.id and l.lang=:lang
            where f.parent in (select factor from TypClusterFactor)
        """, Map.of("lang", lang));
        UtEntityTranslate ut = new UtEntityTranslate(mdb);
        ut.getTranslatedStore(stFactor,"Factor", lang);
        //
        String whe = "t.typ=:t or t.typ=:tp";
        if (id > 0)
            whe = "t.id=:id";

        mdb.loadQuery(st, """
      select * from (
          select t.*, l.name, l.fullName, case when t.typ=:t then 1 else 0 end as isOwn, f.ord
          from TypClusterFactor t
            left join Factor f on t.factor=f.id
            left join TableLang l on l.nameTable='TypClusterFactor' and l.idTable=t.id and l.lang=:lang
          where
      """+whe + ") a order by isOwn desc, ord", Map.of("id", id, "t", typ, "tp", typParent, "lang", lang));

        return ut.getTranslatedStore(st,"TypClusterFactor", lang);
    }

    public Store insertTypClusterFactor(Map<String, Object> params) throws Exception {
        Map<String, Object> rec = UtCnv.toMap(params.get("rec"));
        Store st = mdb.createStore("TypClusterFactor");
        StoreRecord r = st.add(rec);
        //
        long id = mdb.insertRec("TypClusterFactor", r, true);
        //
        FactorDao dao = mdb.createDao(FactorDao.class);
        Map<String, Object> map = dao.loadRec(Map.of("id", r.getLong("factor"), "lang", rec.get("lang"))).get(0).getValues();
        map.put("id", id);
        map.put("table", "TypClusterFactor");
        map.put("lang", rec.get("lang"));

        UtEntityTranslate ut = new UtEntityTranslate(mdb);
        ut.insertToTableLang(map);

        //
        return loadTypClusterFactor(id, UtCnv.toLong(rec.get("typ")), UtCnv.toString(rec.get("lang")));
    }

    public Store updateTypClusterFactor(Map<String, Object> params) throws Exception {
        Map<String, Object> rec = UtCnv.toMap(params.get("rec"));
        long id = UtCnv.toLong(rec.get("id"));
        Store st = mdb.createStore("TypClusterFactor");
        StoreRecord r = st.add(rec);
        //
        mdb.updateRec("TypClusterFactor", r);

        //
        st = mdb.createStore("TypClusterFactor.full");
        mdb.loadQuery(st, """
                  select t.*, f.name, f.fullName, 1 as isOwn from TypClusterFactor t, Factor f
                  where t.factor=f.id and t.id=:id
                """, Map.of("id", id));
        return st;
    }

    public void deleteTypClusterFactor(Map<String, Object> rec) throws Exception {
        long id = UtCnv.toLong(rec.get("id"));
        mdb.deleteRec("TypClusterFactor", id);
        //
        UtEntityTranslate ut = new UtEntityTranslate(mdb);
        ut.deleteFromTableLang("TypClusterFactor", id);
    }

    public Store loadFactors(long typ, String mode, String lang) throws Exception {
        Store st = mdb.createStore("Factor.lang");

        if (mode.equals("ins"))
            mdb.loadQuery(st, """
                select id, '' as name from Factor where parent is null
                    and id not in (
                    select factor from TypClusterFactor where typ=:typ
                )
                order by ord
            """, Map.of("typ", typ));
        if (mode.equals("upd"))
            mdb.loadQuery(st, """
                select id, '' as name from Factor where parent is null order by ord
            """);

        UtEntityTranslate ut = new UtEntityTranslate(mdb);
        return ut.getTranslatedStore(st,"Factor", lang);

    }

}
