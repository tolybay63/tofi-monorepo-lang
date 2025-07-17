/* THIS FILE GENERATED! NOT MODIFY! */
package tofi.mdl.dto;

import jandcode.commons.reflect.*;

/**
 * Группы свойств
* <p>
    * table: PropGr
    */
    public class PropGr_dto {

    private Long id;

    @FieldProps(dict = "FD_AccessLevel")
    private Long accessLevel;

    @FieldProps(size = 30)
    private String cod;

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
    * Уровень доступа
    */
    public Long getAccessLevel() {
    return this.accessLevel;
    }

    public void setAccessLevel(Long v) {
    this.accessLevel = v;
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
    * Группы свойств
    */
    public Long getParent() {
    return this.parent;
    }

    public void setParent(Long v) {
    this.parent = v;
    }

    }
