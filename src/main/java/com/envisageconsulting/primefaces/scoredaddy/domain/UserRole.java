package com.envisageconsulting.primefaces.scoredaddy.domain;

public class UserRole {

    private String code;
    private String description;
    private Role roleName;

    public UserRole(String code, String description) {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role getRoleName() {
        return roleName;
    }

    public void setRoleName(Role roleName) {
        this.roleName = roleName;
    }
}
