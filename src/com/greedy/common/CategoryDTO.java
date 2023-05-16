package com.greedy.common;

public class CategoryDTO {
    private int code;
    private String name;
    private Integer refCategoryCode;

    public CategoryDTO() {
    }

    public CategoryDTO(int categoryCode, String categoryName, Integer refCategoryCode) {
        this.code = categoryCode;
        this.name = categoryName;
        this.refCategoryCode = refCategoryCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int categoryCode) {
        this.code = categoryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String categoryName) {
        this.name = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    @Override
    public String toString() {
        return "CategoryDTO [categoryCode=" + code + ", categoryName=" + name + ", refCategoryCode="
                + refCategoryCode + "]";
    }

}
