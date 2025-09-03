package com.example.lc_demo.mapper.impl;

import com.example.lc_demo.dto.BaseUserDTO;
import com.example.lc_demo.entity.User;
import com.example.lc_demo.enumeration.Role;
import com.example.lc_demo.mapper.EntityMapper;
import com.example.lc_demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("entityMapper")
public class EntityMapperImpl implements EntityMapper {

    private final CourseService courseService;

    @Autowired
    public EntityMapperImpl(final CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public User toNewUserEntity(BaseUserDTO dto, Role role) {
        User user = new User();
        user.setRole(role);

        return commonUserAttrMapper(dto, user);
    }

    @Override
    public User toUpdatedUserEntity(BaseUserDTO dto, User user) {

        return commonUserAttrMapper(dto, user);
    }

    private User commonUserAttrMapper(BaseUserDTO dto, User user) {
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setGroup(dto.getGroup());
        if (dto.getCourses() != null && !dto.getCourses().isEmpty())
            user.setCourses(this.courseService.courseResolver(dto.getCourses()));

        return user;
    }
}
