package com.example.lc_demo;

import com.example.lc_demo.dto.BaseUserDTO;
import com.example.lc_demo.dto.StudentDTO;
import com.example.lc_demo.dto.TeacherDTO;
import com.example.lc_demo.entity.User;
import com.example.lc_demo.enumeration.Role;
import com.example.lc_demo.mapper.DtoMapper;
import com.example.lc_demo.repository.UserRepository;
import com.example.lc_demo.service.impl.ReportServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    private static final String STUDENT_USER_UUID = "9a4d7ca7-52b5-4c31-be72-3254eeb61a98";
    private static final String STUDENT_DTO_UUID = "9a4d7ca7-52b5-4c31-be72-3254eeb61a98";
    private static final String TEACHER_USER_UUID = "6f884f21-55bb-4522-96c4-2e74fdb87daf";
    private static final String TEACHER_DTO_UUID = "6f884f21-55bb-4522-96c4-2e74fdb87daf";

    @Mock
    private UserRepository userRepository;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    public void testCountStudents() {
        when(this.userRepository.countByRole(Role.STUDENT)).thenReturn(5L);
        assertEquals(5L, this.reportService.countStudents());
        verify(this.userRepository).countByRole(Role.STUDENT);
    }

    @Test
    public void testCountTeachers() {
        when(this.userRepository.countByRole(Role.TEACHER)).thenReturn(3L);
        assertEquals(3L, this.reportService.countTeachers());
        verify(this.userRepository).countByRole(Role.TEACHER);
    }

    @Test
    public void testStudentsByCourse() {
        Long courseId = 10L;
        User user = new User();
        user.setId(STUDENT_USER_UUID);
        StudentDTO dto = new StudentDTO();
        dto.setId(STUDENT_DTO_UUID);

        when(this.userRepository.findByRoleAndCourses_Id(Role.STUDENT, courseId))
                .thenReturn(List.of(user));
        when(this.dtoMapper.toStudentDTO(user)).thenReturn(dto);

        List<StudentDTO> out = this.reportService.studentsByCourse(courseId);
        assertEquals(1, out.size());
        assertSame(dto, out.get(0));
        verify(this.userRepository).findByRoleAndCourses_Id(Role.STUDENT, courseId);
        verify(this.dtoMapper).toStudentDTO(user);
    }

    @Test
    public void testStudentsByGroup() {
        String group = "A";
        User user = new User();
        user.setId(STUDENT_USER_UUID);
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(STUDENT_DTO_UUID);

        when(this.userRepository.findByRoleAndGroup(Role.STUDENT, group))
                .thenReturn(List.of(user));
        when(this.dtoMapper.toStudentDTO(user)).thenReturn(studentDTO);

        List<StudentDTO> out = this.reportService.studentsByGroup(group);
        assertEquals(1, out.size());
        assertSame(studentDTO, out.get(0));
        verify(this.userRepository).findByRoleAndGroup(Role.STUDENT, group);
        verify(this.dtoMapper).toStudentDTO(user);
    }

    @Test
    public void testUsersByGroupAndCourse() {
        String group = "B";
        Long courseId = 20L;

        User student = new User();
        student.setRole(Role.STUDENT);
        student.setId(STUDENT_USER_UUID);

        User teacher = new User();
        teacher.setRole(Role.TEACHER);
        teacher.setId(TEACHER_USER_UUID);

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(STUDENT_DTO_UUID);
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(TEACHER_DTO_UUID);

        when(this.userRepository.findByGroupAndCourses_Id(group, courseId))
                .thenReturn(List.of(student, teacher));
        when(this.dtoMapper.toStudentDTO(student)).thenReturn(studentDTO);
        when(this.dtoMapper.toTeacherDTO(teacher)).thenReturn(teacherDTO);

        List<BaseUserDTO> out = this.reportService.usersByGroupAndCourse(group, courseId);
        assertTrue(out.contains(studentDTO));
        assertTrue(out.contains(teacherDTO));

        verify(this.userRepository).findByGroupAndCourses_Id(group, courseId);
    }

    @Test
    public void testStudentsOlderThanInCourse() {
        int age = 18;
        Long courseId = 30L;
        User user = new User();
        user.setId(STUDENT_USER_UUID);
        StudentDTO dto = new StudentDTO();
        dto.setId(STUDENT_DTO_UUID);

        when(this.userRepository.findByRoleAndAgeGreaterThanAndCourses_Id(
                Role.STUDENT, age, courseId))
                .thenReturn(List.of(user));
        when(this.dtoMapper.toStudentDTO(user)).thenReturn(dto);

        List<StudentDTO> out = this.reportService.studentsOlderThanInCourse(age, courseId);
        assertSame(dto, out.get(0));
        verify(this.userRepository).findByRoleAndAgeGreaterThanAndCourses_Id(Role.STUDENT, age, courseId);
    }
}
