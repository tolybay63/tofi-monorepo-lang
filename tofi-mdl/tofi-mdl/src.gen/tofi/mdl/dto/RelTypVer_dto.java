/* THIS FILE GENERATED! NOT MODIFY! */
package tofi.mdl.dto;

import jandcode.commons.datetime.*;

/**
 * Версионная часть отношений между типами объектов
* <p>
    * table: RelTypVer
    */
    public class RelTypVer_dto {

    private Long id;

    private Long ownerVer;

    private XDate dbeg;

    private XDate dend;

    private Integer lastVer;

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
    * Отношения между типами объектов
    */
    public Long getOwnerVer() {
    return this.ownerVer;
    }

    public void setOwnerVer(Long v) {
    this.ownerVer = v;
    }

    /**
    * Начало интервала
    */
    public XDate getDbeg() {
    return this.dbeg;
    }

    public void setDbeg(XDate v) {
    this.dbeg = v;
    }

    /**
    * Конец интервала
    */
    public XDate getDend() {
    return this.dend;
    }

    public void setDend(XDate v) {
    this.dend = v;
    }

    /**
    * lastVer
    */
    public Integer getLastVer() {
    return this.lastVer;
    }

    public void setLastVer(Integer v) {
    this.lastVer = v;
    }

    }
