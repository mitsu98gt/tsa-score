package com.envisageconsulting.primefaces.scoredaddy.domain;

public class UserRole {

    private int id;
    private Role roleName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRoleName() {
        return roleName;
    }

    public void setRoleName(Role roleName) {
        this.roleName = roleName;
    }
}
