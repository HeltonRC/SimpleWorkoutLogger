package com.hrcosta.simpleworkoutlogger.data;

class Exercise {

    private int id;
    private String exName;
    private String exDescription;
    private String exCategory;

    public int getId() {
        return id;
    }

    public String getExName() {
        return exName;
    }

    public String getExDescription() {
        return exDescription;
    }

    public String getExCategory() {
        return exCategory;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public void setExDescription(String exDescription) {
        this.exDescription = exDescription;
    }

    public void setExCategory(String exCategory) {
        this.exCategory = exCategory;
    }

}
