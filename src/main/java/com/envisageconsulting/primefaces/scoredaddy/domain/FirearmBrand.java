package com.envisageconsulting.primefaces.scoredaddy.domain;

public class FirearmBrand {

    private String id;
    private String brand;

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

    public boolean equals(Object obj){
        if(obj instanceof FirearmBrand){
            FirearmBrand firearmBrand = (FirearmBrand)obj;
            if(null != this.id && this.id.equals(firearmBrand.getId())){
                return true;
            }
        }
        return false;
    }
}
