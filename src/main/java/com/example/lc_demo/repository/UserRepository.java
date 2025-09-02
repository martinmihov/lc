package com.example.lc_demo.repository;

import com.example.lc_demo.entity.User;
import com.example.lc_demo.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    long countByRole(Role role);

    List<User> findByRoleAndCourses_Id(Role role, Long courseId);

    List<User> findByRoleAndGroup(Role role, String userGroup);

    List<User> findByGroupAndCourses_Id(String group, Long courseId);

    List<User> findByRoleAndAgeGreaterThanAndCourses_Id(Role role, Integer age, Long courseId);
}
