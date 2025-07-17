/* THIS FILE GENERATED! NOT MODIFY! */
package tofi.mdl.dto;

import jandcode.commons.reflect.*;

/**
 * Факторы и значения факторов
* <p>
    * table: Factor
    */
    public class Factor_dto {

    private Long id;

    @FieldProps(dict = "FD_AccessLevel")
    private Long accessLevel;

    private Long parent;

    @FieldProps(size = 30)
    private String cod;

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
    * Уровень доступа
    */
    public Long getAccessLevel() {
    return this.accessLevel;
    }

    public void setAccessLevel(Long v) {
    this.accessLevel = v;
    }

    /**
    * Факторы и значения факторов
    */
    public Long getParent() {
    return this.parent;
    }

    public void setParent(Long v) {
    this.parent = v;
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
    * Порядковый номер
    */
    public Long getOrd() {
    return this.ord;
    }

    public void setOrd(Long v) {
    this.ord = v;
    }

    }
