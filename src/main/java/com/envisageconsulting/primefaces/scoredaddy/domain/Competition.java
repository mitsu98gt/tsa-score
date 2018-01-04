package com.envisageconsulting.primefaces.scoredaddy.domain;

public class Competition {

    private String id;
    private int accountId;
    private String name;
    private String description;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
        if(obj instanceof Competition){
            Competition competition = (Competition)obj;
            if(null != this.id && this.id.equals(competition.getId())){
                return true;
            }
        }
        return false;
    }

}
