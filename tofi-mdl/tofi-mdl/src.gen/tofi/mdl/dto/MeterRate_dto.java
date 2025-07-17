/* THIS FILE GENERATED! NOT MODIFY! */
package tofi.mdl.dto;

import jandcode.commons.reflect.*;

/**
 * Показатели
* <p>
    * table: MeterRate
    */
    public class MeterRate_dto {

    private Long id;

    private Long meter;

    @FieldProps(size = 30)
    private String cod;

    @FieldProps(dict = "FD_AccessLevel")
    private Long accessLevel;

    private Long parent;

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
    * Измерители
    */
    public Long getMeter() {
    return this.meter;
    }

    public void setMeter(Long v) {
    this.meter = v;
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
    * Показатели
    */
    public Long getParent() {
    return this.parent;
    }

    public void setParent(Long v) {
    this.parent = v;
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
