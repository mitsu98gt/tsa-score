package com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet;

public class Division {

    private boolean stock;
    private boolean unlimited;
    private boolean pocket;
    private boolean woman;
    private boolean senior;
    private boolean junior;
    private boolean limited;
    private boolean revolver;
    private boolean rimfire;

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

    public boolean isLimited() {
        return limited;
    }

    public void setLimited(boolean limited) {
        this.limited = limited;
    }

    public boolean isRevolver() {
        return revolver;
    }

    public void setRevolver(boolean revolver) {
        this.revolver = revolver;
    }

    public boolean isRimfire() {
        return rimfire;
    }

    public void setRimfire(boolean rimfire) {
        this.rimfire = rimfire;
    }
}
