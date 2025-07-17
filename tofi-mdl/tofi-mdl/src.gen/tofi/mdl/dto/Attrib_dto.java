/* THIS FILE GENERATED! NOT MODIFY! */
package tofi.mdl.dto;

import jandcode.commons.reflect.*;

/**
 * Атрибутивные свойства
* <p>
    * table: Attrib
    */
    public class Attrib_dto {

    private Long id;

    @FieldProps(size = 30)
    private String cod;

    @FieldProps(dict = "FD_AccessLevel")
    private Long accessLevel;

    @FieldProps(dict = "FD_AttribValType")
    private Long attribValType;

    private Boolean isMultiLang;

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
    * Значения атрибута
    */
    public Long getAttribValType() {
    return this.attribValType;
    }

    public void setAttribValType(Long v) {
    this.attribValType = v;
    }

    /**
    * Многоязычность
    */
    public Boolean getIsMultiLang() {
    return this.isMultiLang;
    }

    public void setIsMultiLang(Boolean v) {
    this.isMultiLang = v;
    }

    }
