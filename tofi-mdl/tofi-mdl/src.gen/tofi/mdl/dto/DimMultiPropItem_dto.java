/* THIS FILE GENERATED! NOT MODIFY! */
package tofi.mdl.dto;

import jandcode.commons.reflect.*;

/**
 * Элементы измерения многомерного свойства
* <p>
    * table: DimMultiPropItem
    */
    public class DimMultiPropItem_dto {

    private Long id;

    private Long dimMultiProp;

    @FieldProps(size = 30)
    private String cod;

    private Long parent;

    private Long prop;

    @FieldProps(dict = "FD_MultiValEntityType")
    private Long multiEntityType;

    @FieldProps(size = 100)
    private String multiPropItemTag;

    private Long ord;

    /**
    * id
    */
    public Long getId() {
    return this.id;
    }

    public void setId(Long v) {
    this.id = v;
    }

    /**
    * Измерения многомерного свойства
    */
    public Long getDimMultiProp() {
    return this.dimMultiProp;
    }

    public void setDimMultiProp(Long v) {
    this.dimMultiProp = v;
    }

    /**
    * Код
    */
    public String getCod() {
    return this.cod;
    }

    public void setCod(String v) {
    this.cod = v;
    }

    /**
    * Элементы измерения многомерного свойства
    */
    public Long getParent() {
    return this.parent;
    }

    public void setParent(Long v) {
    this.parent = v;
    }

    /**
    * Свойства
    */
    public Long getProp() {
    return this.prop;
    }

    public void setProp(Long v) {
    this.prop = v;
    }

    /**
    * Тип значения ячейки многомерного свойства
    */
    public Long getMultiEntityType() {
    return this.multiEntityType;
    }

    public void setMultiEntityType(Long v) {
    this.multiEntityType = v;
    }

    /**
    * multiPropItemTag
    */
    public String getMultiPropItemTag() {
    return this.multiPropItemTag;
    }

    public void setMultiPropItemTag(String v) {
    this.multiPropItemTag = v;
    }

    /**
    * Порядковый номер
    */
    public Long getOrd() {
    return this.ord;
    }

    public void setOrd(Long v) {
    this.ord = v;
    }

    }
