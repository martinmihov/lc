package com.example.lc_demo.service;

import com.example.lc_demo.dto.BaseUserDTO;
import com.example.lc_demo.dto.StudentDTO;

import java.util.List;

public interface ReportService {

    long countStudents();

    long countTeachers();

    long countCoursesByType(String courseType);

    List<StudentDTO> studentsByCourse(Long courseId);

    List<StudentDTO> studentsByGroup(String group);

    List<BaseUserDTO> usersByGroupAndCourse(String group, Long courseId);

    List<StudentDTO> studentsOlderThanInCourse(Integer age, Long courseId);
}
