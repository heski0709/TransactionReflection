package com.greedy.common;

import java.util.List;

public class CategoryAndMenuDTO {
    private int code;
    private String name;
    private Integer refCategoryCode;
    private List<MenuDTO> menu;

    public CategoryAndMenuDTO() {
    }

    public CategoryAndMenuDTO(int code, String name, Integer refCategoryCode, List<MenuDTO> menu) {
        this.code = code;
        this.name = name;
        this.refCategoryCode = refCategoryCode;
        this.menu = menu;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    public List<MenuDTO> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuDTO> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "CategoryAndMenuDTO [code=" + code + ", name=" + name + ", refCategoryCode=" + refCategoryCode
                + ", menu=" + menu + "]";
    }

}
