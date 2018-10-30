package com.api.genealogy.service.relations;

public class Key {
    private int lifeDistance;
    private Boolean gender;
    private Boolean originOlder;
    private Boolean inFamily;

    public Key() {
    }

    public Key(int lifeDistance, Boolean gender, Boolean originOlder, Boolean inFamily) {
        this.gender = gender;
        this.lifeDistance = lifeDistance;
        this.originOlder = originOlder;
        this.inFamily = inFamily;
    }

    public int getLifeDistance() {
        return lifeDistance;
    }

    public void setLifeDistance(int lifeDistance) {
        this.lifeDistance = lifeDistance;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Boolean getOriginOlder() {
        return originOlder;
    }

    public void setOriginOlder(Boolean originOlder) {
        this.originOlder = originOlder;
    }

    public Boolean getInFamily() {
        return inFamily;
    }

    public void setInFamily(Boolean inFamily) {
        this.inFamily = inFamily;
    }

    public String getKey(){
        return "" + this.getLifeDistance() + this.getGender()+ this.getOriginOlder() + this.getInFamily();
    }
}
