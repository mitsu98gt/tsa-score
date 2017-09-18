package com.envisageconsulting.primefaces.scoredaddy.domain;

public enum Role {

    A ("ADMIN"),
    M ("MANAGER"),
    C ("COMPETITOR"),
    ;

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    
}
