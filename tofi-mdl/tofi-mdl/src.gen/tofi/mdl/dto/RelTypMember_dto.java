/* THIS FILE GENERATED! NOT MODIFY! */
package tofi.mdl.dto;

import jandcode.commons.reflect.*;

/**
 * Участники отношения между типами объектов
* <p>
    * table: RelTypMember
    */
    public class RelTypMember_dto {

    private Long id;

    private Long relTyp;

    private Integer card;

    @FieldProps(dict = "FD_MemberType")
    private Long memberType;

    private Long typ;

    private Long relTypMemb;

    private Long role;

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
    * Отношения между типами объектов
    */
    public Long getRelTyp() {
    return this.relTyp;
    }

    public void setRelTyp(Long v) {
    this.relTyp = v;
    }

    /**
    * Число кардинальности
    */
    public Integer getCard() {
    return this.card;
    }

    public void setCard(Integer v) {
    this.card = v;
    }

    /**
    * Тип участника отношения между типами объектов
    */
    public Long getMemberType() {
    return this.memberType;
    }

    public void setMemberType(Long v) {
    this.memberType = v;
    }

    /**
    * Типы объектов
    */
    public Long getTyp() {
    return this.typ;
    }

    public void setTyp(Long v) {
    this.typ = v;
    }

    /**
    * Отношения между типами объектов
    */
    public Long getRelTypMemb() {
    return this.relTypMemb;
    }

    public void setRelTypMemb(Long v) {
    this.relTypMemb = v;
    }

    /**
    * Роли типов объектов и отношений между типами объектов
    */
    public Long getRole() {
    return this.role;
    }

    public void setRole(Long v) {
    this.role = v;
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
