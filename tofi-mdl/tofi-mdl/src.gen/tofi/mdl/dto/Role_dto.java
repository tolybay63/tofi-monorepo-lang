/* THIS FILE GENERATED! NOT MODIFY! */
package tofi.mdl.dto;

import jandcode.commons.reflect.*;

/**
 * Роли типов объектов и отношений между типами объектов
* <p>
    * table: Role
    */
    public class Role_dto {

    private Long id;

    @FieldProps(size = 30)
    private String cod;

    @FieldProps(dict = "FD_AccessLevel")
    private Long accessLevel;

    @FieldProps(size = 200)
    private String roleTag;

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
    * roleTag
    */
    public String getRoleTag() {
    return this.roleTag;
    }

    public void setRoleTag(String v) {
    this.roleTag = v;
    }

    }
