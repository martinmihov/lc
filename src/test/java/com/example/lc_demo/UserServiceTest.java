package com.example.lc_demo;

import com.example.lc_demo.dto.StudentDTO;
import com.example.lc_demo.dto.TeacherDTO;
import com.example.lc_demo.entity.User;
import com.example.lc_demo.enumeration.Role;
import com.example.lc_demo.mapper.DtoMapper;
import com.example.lc_demo.mapper.EntityMapper;
import com.example.lc_demo.repository.UserRepository;
import com.example.lc_demo.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String STUDENT_USER_UUID = "9a4d7ca7-52b5-4c31-be72-3254eeb61a98";
    private static final String TEACHER_USER_UUID = "6f884f21-55bb-4522-96c4-2e74fdb87daf";

    @Mock
    private UserRepository userRepository;

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private EntityMapper entityMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateStudent() {
        StudentDTO inStudentDTO = new StudentDTO();
        User newEntity = new User();
        User savedEntity = new User();
        StudentDTO outStudentDTO = new StudentDTO();

        when(this.entityMapper.toNewUserEntity(inStudentDTO, Role.STUDENT)).thenReturn(newEntity);
        when(this.userRepository.save(newEntity)).thenReturn(savedEntity);
        when(this.dtoMapper.toStudentDTO(savedEntity)).thenReturn(outStudentDTO);

        assertSame(outStudentDTO, this.userService.createStudent(inStudentDTO));
        verify(this.entityMapper).toNewUserEntity(inStudentDTO, Role.STUDENT);
        verify(this.userRepository).save(newEntity);
        verify(this.dtoMapper).toStudentDTO(savedEntity);
    }

    @Test
    public void testCreateTeacher() {
        TeacherDTO inTeacherDTO = new TeacherDTO();
        User newEntity = new User();
        User savedEntity = new User();
        TeacherDTO outTeacherDTO = new TeacherDTO();

        when(this.entityMapper.toNewUserEntity(inTeacherDTO, Role.TEACHER)).thenReturn(newEntity);
        when(this.userRepository.save(newEntity)).thenReturn(savedEntity);
        when(this.dtoMapper.toTeacherDTO(savedEntity)).thenReturn(outTeacherDTO);

        assertSame(outTeacherDTO, userService.createTeacher(inTeacherDTO));
        verify(this.entityMapper).toNewUserEntity(inTeacherDTO, Role.TEACHER);
        verify(this.userRepository).save(newEntity);
        verify(this.dtoMapper).toTeacherDTO(savedEntity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateStudentNotFound() {
        this.userService.updateStudent(new StudentDTO());
        verify(this.userRepository).findById(STUDENT_USER_UUID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTeacherNotFound() {
        this.userService.updateTeacher(new TeacherDTO());
        verify(this.userRepository).findById(TEACHER_USER_UUID);
    }

    @Test
    public void testDeleteUserSuccess() {
        this.userService.deleteUser(STUDENT_USER_UUID);
        verify(this.userRepository).deleteById(STUDENT_USER_UUID);
    }

    @Test
    public void testGetStudentByIdSuccess() {
        User user = new User();
        user.setRole(Role.STUDENT);
        StudentDTO dto = new StudentDTO();

        when(this.userRepository.findById(STUDENT_USER_UUID)).thenReturn(Optional.of(user));
        when(this.dtoMapper.toStudentDTO(user)).thenReturn(dto);

        assertSame(dto, this.userService.getUser(STUDENT_USER_UUID));
        verify(this.userRepository).findById(STUDENT_USER_UUID);
        verify(this.dtoMapper).toStudentDTO(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStudentByIdNotFound() {
        when(this.userRepository.findById(STUDENT_USER_UUID)).thenReturn(Optional.empty());
        this.userService.getUser(STUDENT_USER_UUID);
        verify(this.userRepository).findById(STUDENT_USER_UUID);
    }

    @Test
    public void testGetTeacherByIdSuccess() {
        User userWithRoleTeacher = new User();
        userWithRoleTeacher.setRole(Role.TEACHER);
        TeacherDTO dto = new TeacherDTO();

        when(this.userRepository.findById(TEACHER_USER_UUID)).thenReturn(Optional.of(userWithRoleTeacher));
        when(this.dtoMapper.toTeacherDTO(userWithRoleTeacher)).thenReturn(dto);

        assertSame(dto, this.userService.getUser(TEACHER_USER_UUID));

        verify(this.userRepository).findById(TEACHER_USER_UUID);
        verify(this.dtoMapper).toTeacherDTO(userWithRoleTeacher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTeacherByIdNotFound() {
        when(this.userRepository.findById(TEACHER_USER_UUID)).thenReturn(Optional.empty());
        this.userService.getUser(TEACHER_USER_UUID);
    }
}
