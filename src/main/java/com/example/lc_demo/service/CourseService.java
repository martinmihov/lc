package com.example.lc_demo.service;

import com.example.lc_demo.dto.CourseDTO;
import com.example.lc_demo.entity.Course;

import java.util.Set;

public interface CourseService {

    Set<Course> courseResolver(Set<CourseDTO> coursesDTO);
}
