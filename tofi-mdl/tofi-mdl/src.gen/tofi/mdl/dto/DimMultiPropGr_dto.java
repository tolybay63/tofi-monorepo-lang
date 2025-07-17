/* THIS FILE GENERATED! NOT MODIFY! */
package tofi.mdl.dto;

import jandcode.commons.reflect.*;

/**
 * Группа измерений многомерного свойства
* <p>
    * table: DimMultiPropGr
    */
    public class DimMultiPropGr_dto {

    private Long id;

    @FieldProps(size = 30)
    private String cod;

    @FieldProps(dict = "FD_AccessLevel")
    private Long accessLevel;

    private Long parent;

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
    * Группа измерений многомерного свойства
    */
    public Long getParent() {
    return this.parent;
    }

    public void setParent(Long v) {
    this.parent = v;
    }

    }
