package com.example.lc_demo.repository;


import com.example.lc_demo.entity.Course;
import com.example.lc_demo.enumeration.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    long countByType(CourseType type);

    Optional<Course> findByNameAndType(String name, CourseType type);
}
