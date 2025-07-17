/* THIS FILE GENERATED! NOT MODIFY! */
package tofi.mdl.dto;

import jandcode.commons.reflect.*;

/**
 * Измерения многомерного свойства
* <p>
    * table: DimMultiProp
    */
    public class DimMultiProp_dto {

    private Long id;

    private Long DimMultiPropGr;

    @FieldProps(size = 30)
    private String cod;

    @FieldProps(dict = "FD_AccessLevel")
    private Long accessLevel;

    @FieldProps(dict = "FD_DimMultiPropType")
    private Long dimMultiPropType;

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
    * Группа измерений многомерного свойства
    */
    public Long getDimMultiPropGr() {
    return this.DimMultiPropGr;
    }

    public void setDimMultiPropGr(Long v) {
    this.DimMultiPropGr = v;
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
    * Уровень доступа
    */
    public Long getAccessLevel() {
    return this.accessLevel;
    }

    public void setAccessLevel(Long v) {
    this.accessLevel = v;
    }

    /**
    * Тип измерения многомерного свойства
    */
    public Long getDimMultiPropType() {
    return this.dimMultiPropType;
    }

    public void setDimMultiPropType(Long v) {
    this.dimMultiPropType = v;
    }

    }
