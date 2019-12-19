package com.envisageconsulting.primefaces.scoredaddy.domain;

public class Tournament {

    private int id;
    private int account_id;
    private String name;
    private String description;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean equals(Object obj){
        if(obj instanceof Tournament){
            Tournament tournament = (Tournament)obj;
            if(0 != this.id && this.id == tournament.getId()){
                return true;
            }
        }
        return false;
    }
}
