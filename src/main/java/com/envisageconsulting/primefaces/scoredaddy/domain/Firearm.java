package com.envisageconsulting.primefaces.scoredaddy.domain;

public class Firearm {

    private String id;
    private String brand;
    private String model;
    private String caliber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }

    public boolean equals(Object obj){
        if(obj instanceof Firearm){
            Firearm firearm = (Firearm)obj;
            if(null != this.id && this.id.equals(firearm.getId())){
                return true;
            }
        }
        return false;
    }

    public int hashCode(){
        return this.id.hashCode();
    }

}
