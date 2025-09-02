package com.example.lc_demo.mapper;

import com.example.lc_demo.dto.BaseUserDTO;
import com.example.lc_demo.entity.User;
import com.example.lc_demo.enumeration.Role;

public interface EntityMapper {

    User toNewUserEntity(BaseUserDTO dto, Role role);

    User toUpdatedUserEntity(BaseUserDTO dto, User user);
}
