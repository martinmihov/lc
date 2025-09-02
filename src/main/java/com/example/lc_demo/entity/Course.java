package com.example.lc_demo.entity;

import com.example.lc_demo.enumeration.CourseType;
import jakarta.persistence.*;

@Entity
@Table(name = "COURSES",
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueTypeAndName", columnNames = {"type", "name"})
        })
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CourseType type;

    @Column
    private String name;

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
