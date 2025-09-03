package com.example.lc_demo.mapper.impl;

import com.example.lc_demo.dto.BaseUserDTO;
import com.example.lc_demo.dto.CourseDTO;
import com.example.lc_demo.dto.StudentDTO;
import com.example.lc_demo.dto.TeacherDTO;
import com.example.lc_demo.entity.User;
import com.example.lc_demo.mapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component("dtoMapper")
public class DtoMapperImpl implements DtoMapper {

    public StudentDTO toStudentDTO(User user) {
        StudentDTO dto = new StudentDTO();
        mapBaseUser(user, dto);

        return dto;
    }

    public TeacherDTO toTeacherDTO(User user) {
        TeacherDTO dto = new TeacherDTO();
        mapBaseUser(user, dto);

        return dto;
    }

    private void mapBaseUser(User user, BaseUserDTO dto) {
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setGroup(user.getGroup());
        dto.setCourses(user.getCourses() == null ? Collections.emptySet() :
                user.getCourses().stream().map(c -> {
                    CourseDTO courseDTO = new CourseDTO();
                    courseDTO.setId(c.getId());
                    courseDTO.setName(c.getName());
                    courseDTO.setType(c.getType());
                    return courseDTO;
                }).collect(Collectors.toSet()));
    }
}
