package com.example.lc_demo.service.impl;

import com.example.lc_demo.dto.BaseUserDTO;
import com.example.lc_demo.dto.StudentDTO;
import com.example.lc_demo.enumeration.CourseType;
import com.example.lc_demo.enumeration.Role;
import com.example.lc_demo.mapper.DtoMapper;
import com.example.lc_demo.repository.CourseRepository;
import com.example.lc_demo.repository.UserRepository;
import com.example.lc_demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final DtoMapper dtoMapper;

    @Autowired
    public ReportServiceImpl(final UserRepository userRepository,
                             final CourseRepository courseRepository,
                             final DtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public long countStudents() {
        return this.userRepository.countByRole(Role.STUDENT);
    }

    @Override
    public long countTeachers() {
        return this.userRepository.countByRole(Role.TEACHER);
    }

    @Override
    public long countCoursesByType(String courseType) {
        CourseType type = CourseType.valueOf(courseType);

        return this.courseRepository.countByType(type);
    }

    @Override
    public List<StudentDTO> studentsByCourse(Long courseId) {

        return this.userRepository.findByRoleAndCourses_Id(Role.STUDENT, courseId)
                .stream().map(this.dtoMapper::toStudentDTO).toList();
    }

    @Override
    public List<StudentDTO> studentsByGroup(String group) {

        return this.userRepository.findByRoleAndGroup(Role.STUDENT, group)
                .stream().map(this.dtoMapper::toStudentDTO).toList();
    }

    @Override
    public List<BaseUserDTO> usersByGroupAndCourse(String group, Long courseId) {

        return this.userRepository.findByGroupAndCourses_Id(group, courseId)
                .stream()
                .map(u -> u.getRole() == Role.STUDENT
                        ? this.dtoMapper.toStudentDTO(u)
                        : this.dtoMapper.toTeacherDTO(u))
                .toList();
    }

    @Override
    public List<StudentDTO> studentsOlderThanInCourse(Integer age, Long courseId) {

        return this.userRepository.findByRoleAndAgeGreaterThanAndCourses_Id(Role.STUDENT, age, courseId)
                .stream().map(this.dtoMapper::toStudentDTO).toList();
    }
}
