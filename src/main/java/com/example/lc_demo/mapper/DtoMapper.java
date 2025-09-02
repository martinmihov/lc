package com.example.lc_demo.mapper;

import com.example.lc_demo.dto.StudentDTO;
import com.example.lc_demo.dto.TeacherDTO;
import com.example.lc_demo.entity.User;

public interface DtoMapper {

    StudentDTO toStudentDTO(User user);

    TeacherDTO toTeacherDTO(User user);
}
