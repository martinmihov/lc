package com.example.lc_demo.service.impl;

import com.example.lc_demo.dto.CourseDTO;
import com.example.lc_demo.entity.Course;
import com.example.lc_demo.repository.CourseRepository;
import com.example.lc_demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(final CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Set<Course> courseResolver(Set<CourseDTO> coursesDTO) {
        Set<Course> resolvedCourses = coursesDTO.stream()
                .map(courseDTO -> {
                    Optional<Course> existing = this.courseRepository.findByNameAndType(courseDTO.getName(), courseDTO.getType());
                    return existing.orElseGet(() -> {
                        Course newCourse = new Course();
                        newCourse.setName(courseDTO.getName());
                        newCourse.setType(courseDTO.getType());
                        return this.courseRepository.save(newCourse);
                    });
                }).collect(Collectors.toSet());

        return resolvedCourses;
    }
}
