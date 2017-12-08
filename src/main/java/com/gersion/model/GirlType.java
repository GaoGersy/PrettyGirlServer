package com.gersion.model;

public class GirlType {
    private Integer id;

    private Integer girlType;

    private String categoryName;

    public GirlType(Integer id, Integer girlType, String categoryName) {
        this.id = id;
        this.girlType = girlType;
        this.categoryName = categoryName;
    }

    public GirlType() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGirlType() {
        return girlType;
    }

    public void setGirlType(Integer girlType) {
        this.girlType = girlType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }
}