package com.example.lc_demo.dto;

import com.example.lc_demo.enumeration.CourseType;

public class CourseDTO {
    private Long id;
    private String name;
    private CourseType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }
}
