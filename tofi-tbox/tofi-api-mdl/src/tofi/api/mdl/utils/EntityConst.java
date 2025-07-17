package tofi.api.mdl.utils;
import jandcode.commons.UtLang;
import jandcode.commons.UtString;
import jandcode.commons.collect.LinkedHashMapNoCase;
import jandcode.commons.error.XError;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntityConst {

    /**
     * Единицы измерения
     */
    public static long Measure = 1;

    /**
     * Атрибут
     */
    public static long Attrib = 2;

    /**
     * Факторы
     */
    public static long Factor = 3;

    /**
     * Значения факторов
     */
    public static long FactorVal = 4;

    /**
     * Измерители
     */
    public static long Meter = 5;

    /**
     * Показатели
     */
    public static long MeterRate = 6;

    /**
     * Шкалы
     */
    public static long Scale = 7;

    /**
     * Настройки шкалы
     */
    public static long ScaleAsgn = 8;

    /**
     * Роли типы объектов
     */
    public static long Role = 9;

    /**
     * Типы объектов
     */
    public static long Typ = 10;

    /**
     * Классы объектов
     */
    public static long Cls = 11;


    /**
     * Системы кодирования
     */
    public static long SysCoding = 12;

    /*
     * Группы свойств
     */
    public static long PropGr = 13;

    /*
     * Свойства
     */
    public static long Prop = 14;

    /*
     * Группы измерений многомерного свойства
     */
    public static long DimMultiPropGr = 15;

    /*
     * Измерения многомерного свойства
     */
    public static long DimMultiProp = 16;

    /*
     * Элементы измерения многомерного свойства
     */
    public static long DimMultiPropItem = 17;

    /*
     * Группы многомерных свойств
     */
    public static long MultiPropGr = 18;

    /*
     * Многомерные свойства
     */
    public static long MultiProp = 19;

    /*
     * Группы характеристических свойств типа объектов
     */
    public static long TypCharGr = 20;

    /**
     * Отношения между типами объектов
     */
    public static long RelTyp = 21;

    /*
     * отношения между классами
     */
    public static long RelCls = 22;

    /*
     * Группы характеристических свойств отношения между типами объектов
     */
    public static long RelTypCharGr = 23;

    /*
     * Измерение периодов
     */
    public static long DimPeriod = 24;

    /*
     * Группы измерений свойств
     */
    public static long DimPropGr = 25;

    /*
     * Измерения свойств
     */
    public static long DimProp = 26;
    /*
     * Группы измерений объектов и отношений
     */
    public static long DimObjGr = 27;

    /*
     * Измерения объектов и отношений
     */
    public static long DimObj = 28;

    /*
     * Перечень базы данных
     */
    public static long DataBase = 29;

    /*
     * Плоские таблицы
     */
    public static long FlatTable = 30;

    //...

    /**
     * Информация о сущности
     */
    public static class EntityInfo {

        private long numConst;
        private String codTemplate;
        private String sign;
        private String tableName;
        private String text;
        private boolean hasVer;
        private String icon;
        private int vis;

        /**
         * Числовая константа для сущности = код значения фактора с id=50
         */
        public long getNumConst() {
            return numConst;
        }

        /**
         * Шаблон кода
         */
        public String getCodTemplate() {
            return codTemplate;
        }

        /**
         * Имя таблицы
         */
        public String getTableName() {
            return tableName;
        }

        /**
         * Код сущности
         */
        public String getSign() {
            return sign;
        }

        /**
         * Наименование сущности
         */
        public String getText() {
            return text;
        }

        /**
         * Имеет версию?
         */
        public boolean getHasVer() {
            return hasVer;
        }

        public int getVis() {
            return vis;
        }

        public String getIcon() {
            return icon;
        }
    }

    private static final LinkedHashMap<Long, EntityInfo> entityInfos = new LinkedHashMap<Long, EntityInfo>();
    private static final LinkedHashMapNoCase<EntityInfo> entityInfosBySign = new LinkedHashMapNoCase<EntityInfo>();

    public static String genCodPref = "_";

    private static void add(long numConst, String codTemplate, String sign, String tableName, String text,
                            boolean hasVer, int vis, String icon) {
        EntityInfo z = new EntityInfo();
        z.numConst = numConst;
        z.codTemplate = genCodPref + codTemplate;
        z.sign = sign;
        z.tableName = tableName;
        z.text = text;
        z.hasVer = hasVer;
        z.icon = icon;
        z.vis = vis;
        //
        if (entityInfos.containsKey(numConst)) {
            throw new XError("TofiEntityConst: duplicate numConst {0}", numConst);
        }
        entityInfos.put(numConst, z);
        //
        if (entityInfosBySign.containsKey(z.getSign())) {
            throw new XError("TofiEntityConst: duplicate tableName {0}", numConst);
        }
        entityInfosBySign.put(z.getSign(), z);
    }

    static {

        // дополнительная информация о сущностях
        // false: Неверсионный  true: Версионный
        add(Measure, "M_#", "Measure", "Measure", "Единица измерения", false, 1, "measure");
        add(Attrib, "A_#", "Attrib", "Attrib", "Атрибут", false, 1, "attrib");
        add(Factor, "F_#", "Factor", "Factor", "Фактор", false, 1, "factor");
        add(FactorVal, "FV_#", "FactorVal", "Factor", "Значение фактора", false, 1, "factorval");
        add(Meter, "I_#", "Meter", "Meter", "Измеритель", false, 1, "meter");
        add(MeterRate, "IP_#", "MeterRate", "MeterRate", "Показатель", false, 1, "meterrate");
        add(Role, "R_#", "Role", "Role", "Роль типа объектов и отношений", false, 1, "role");
        add(Typ, "T_#", "Typ", "Typ", "Тип объектов", true, 1, "typ");
        add(Cls, "C_#", "Cls", "Cls", "Класс объектов", true, 1, "cls");
        //add(Obj, "O_#", "Obj", "Obj", "Объект", true, 1, "obj");
        add(TypCharGr, "TG_#", "TypCharGr", "TypCharGr", "Группа характеристических свойств типа объектов", false, 1, "typchargr");
        add(RelTyp, "RT_#", "RelTyp", "RelTyp", "Отношение между типами объектов", true, 1, "reltyp");
        add(SysCoding, "SC_#", "SysCoding", "SysCoding", "Система кодирования", false, 1, "syscoding");
        //add(RelObj, "RO_#", "RelObj", "RelObj", "Отношение между объектов", true, 1, "relobj");
        add(RelTypCharGr, "RG_#", "RelTypCharGr", "RelTypCharGr", "Группа характеристических свойств отношения между типами объектов", false, 1, "reltypchargr");
        add(PropGr, "PG_#", "PropGr", "PropGr", "Группа свойств", false, 1, "propgr");
        add(Prop, "P_#", "Prop", "Prop", "Свойство", false, 0, "prop");
        add(DimMultiPropGr, "DMPG_#", "DimMultiPropGr", "DimMultiPropGr", "Группа измерений многомерного свойства", false, 1, "dimmultiprop");
        add(DimMultiProp, "DMP_#", "DimMultiProp", "DimMultiProp", "Измерение многомерного свойства", false, 1, "dimmultiprop");
        add(Scale, "S_#", "Scale", "Scale", "Шкала", false, 1, "scale");
        add(ScaleAsgn, "SA_#", "ScaleAsgn", "scaleasgn", "Настройка шкалы", false, 1, "scaleasgn");
        add(MultiPropGr, "MPG_#", "MultiPropGr", "MultiPropGr", "Группа многомерных свойств", false, 1, "multipropgr");
        add(MultiProp, "MP_#", "MultiProp", "MultiProp", "Многомерные свойства", false, 1, "multiprop");
        add(DimPeriod, "D_#", "DimPeriod", "DimPeriod", "Измерение периодов", false, 1, "dimperiod");
        add(DimPropGr, "DPG_#", "DimPropGr", "DimPropGr", "Группа измерения свойств", false, 1, "dimpropgr");
        add(DimProp, "DP_#", "DimProp", "DimProp", "Измерение свойств", false, 1, "dimprop");
        add(DimObjGr, "DOG_#", "DimObjGr", "DimObjGr", "Группа измерений объектов и отношений", false, 1, "dimobjgr");
        add(DimObj, "DO_#", "DimObj", "DimObj", "Измерение объектов и отношений", false, 1, "dimobj");
        add(DataBase, "DB_#", "DataBase", "DataBase", "Перечень базы данных", false, 1, "database");
        add(RelCls, "RC_#", "RelCls", "RelCls", "Класс отношений", true, 1, "relcls");
        add(FlatTable, "FT_#", "FlatTable", "FlatTable", "Плоские таблицы", false, 1, "flattable");
        add(DimMultiPropItem, "DMPI_#", "DimMultiPropItem", "DimMultiPropItem", "Элементы измерения многомерного свойства", false, 1, "dimmultipropitem");
    }

    ////// утилиты

    /**
     * Список сущностей
     */
    public static Collection<EntityInfo> getEntityInfos() {
        return entityInfos.values();
    }

    /**
     * Информация о сущности по коду сущности
     */
    public static EntityInfo getEntityInfo(long ent) {
        EntityInfo z = entityInfos.get(ent);
        if (z == null) {
            throw new XError(UtLang.t("Не найдена сущность с кодом {0}", ent));
        }
        return z;
    }

    /**
     * Информация о сущности по коду сущности
     */
    public static EntityInfo getEntityInfo(String sign) {
        EntityInfo z = entityInfosBySign.get(sign);
        if (z == null) {
            throw new XError(UtLang.t("Не найдена сущность с именем {0}", sign));
        }
        return z;
    }

    /**
     * Шаблон кода сущности по коду сущности
     */
    public static String getCodTemplate(long ent) {
        return getEntityInfo(ent).getCodTemplate();
    }

    /**
     * Числовая константа сущности для имени таблицы
     */
    public static long getNumConst(String sign) {
        return getEntityInfo(sign).getNumConst();
    }

    /**
     * Проверка, что код сгенерирован
     *
     * @param ent для кого
     * @param cod код
     * @return true - сгенерирован
     */
    public static boolean isCodGenerated(long ent, String cod) {
        if (UtString.empty(cod)) {
            return false;
        }
        String pat = getCodTemplate(ent);
        pat = pat.replace("#", "\\d+");
        Pattern p = Pattern.compile(pat, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(cod);
        return m.matches();
    }

    /**
     * Проверка, что код сгенерирован. Проверяются все варианты.
     *
     * @param cod код
     * @return true - сгенерирован
     */
    public static boolean isCodGenerated(String cod) {
        if (UtString.empty(cod)) {
            return false;
        }
        for (EntityInfo z : getEntityInfos()) {
            if (isCodGenerated(z.getNumConst(), cod)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Генерация кода
     *
     * @param ent для кого
     * @param id  для какого id
     * @return сгенерированный код
     */
    public static String generateCod(long ent, long id) {
        return getCodTemplate(ent).replace("#", "" + id);
    }

    /**
     * Проверка, что код соответсвует шаблону (состоит из анг. алф., цифр и знаков: -, _, /)
     *
     * @param cod код
     * @return true - соответсвует
     */
    public static boolean isCodValid(String cod) {
        if (cod.startsWith(genCodPref)) return false;

        if (!UtString.empty(cod)) {
            String pText = "[a-zA-Z0-9_\\-\\/\\.]*";
            Pattern p = Pattern.compile(pText, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(cod);

            return m.matches();
        } else {
            return true;
        }
    }


}
