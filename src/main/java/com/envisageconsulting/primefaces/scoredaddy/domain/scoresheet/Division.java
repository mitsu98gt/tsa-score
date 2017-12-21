package com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet;

public class Division {

    private boolean stock;
    private boolean unlimited;
    private boolean pocket;
    private boolean woman;
    private boolean senior;
    private boolean junior;

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public boolean isUnlimited() {
        return unlimited;
    }

    public void setUnlimited(boolean unlimited) {
        this.unlimited = unlimited;
    }

    public boolean isPocket() {
        return pocket;
    }

    public void setPocket(boolean pocket) {
        this.pocket = pocket;
    }

    public boolean isWoman() {
        return woman;
    }

    public void setWoman(boolean woman) {
        this.woman = woman;
    }

    public boolean isSenior() {
        return senior;
    }

    public void setSenior(boolean senior) {
        this.senior = senior;
    }

    public boolean isJunior() {
        return junior;
    }

    public void setJunior(boolean junior) {
        this.junior = junior;
    }
}
