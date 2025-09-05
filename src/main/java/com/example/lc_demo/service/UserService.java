package com.example.lc_demo.service;

import com.example.lc_demo.dto.BaseUserDTO;
import com.example.lc_demo.dto.StudentDTO;
import com.example.lc_demo.dto.TeacherDTO;

public interface UserService {

    StudentDTO createStudent(final StudentDTO dto);

    StudentDTO updateStudent(StudentDTO dto);

    TeacherDTO createTeacher(final TeacherDTO dto);

    TeacherDTO updateTeacher(TeacherDTO dto);

    void deleteUser(String id);

    void deleteTeacher(String id);

    BaseUserDTO getUser(String id);
}
