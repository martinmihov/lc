package com.example.lc_demo.service.impl;

import com.example.lc_demo.dto.BaseUserDTO;
import com.example.lc_demo.dto.StudentDTO;
import com.example.lc_demo.dto.TeacherDTO;
import com.example.lc_demo.entity.User;
import com.example.lc_demo.enumeration.Role;
import com.example.lc_demo.mapper.DtoMapper;
import com.example.lc_demo.mapper.EntityMapper;
import com.example.lc_demo.repository.UserRepository;
import com.example.lc_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;
    private final DtoMapper dtoMapper;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final EntityMapper entityMapper,
                           final DtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public StudentDTO createStudent(final StudentDTO dto) {
        User newUserEntity = this.entityMapper.toNewUserEntity(dto, Role.STUDENT);
        User savedUser = this.userRepository.save(newUserEntity);

        return this.dtoMapper.toStudentDTO(savedUser);
    }

    @Override
    public TeacherDTO createTeacher(final TeacherDTO dto) {
        User newUserEntity = this.entityMapper.toNewUserEntity(dto, Role.TEACHER);
        User savedUser = this.userRepository.save(newUserEntity);

        return this.dtoMapper.toTeacherDTO(savedUser);
    }

    @Override
    public StudentDTO updateStudent(StudentDTO dto) {
        Optional<User> foundStudent = this.userRepository.findById(dto.getId());
        if (foundStudent.isEmpty() || foundStudent.get().getRole() != Role.STUDENT) {
            throw new IllegalArgumentException("Student not found with id " + dto.getId());
        }
        User userEntity = this.entityMapper.toUpdatedUserEntity(dto, foundStudent.get());
        User updatedStudent = this.userRepository.save(userEntity);

        return this.dtoMapper.toStudentDTO(updatedStudent);
    }

    @Override
    public TeacherDTO updateTeacher(TeacherDTO dto) {
        Optional<User> foundTeacher = this.userRepository.findById(dto.getId());
        if (foundTeacher.isEmpty() || foundTeacher.get().getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("Teacher not found with id " + dto.getId());
        }
        User userEntity = this.entityMapper.toUpdatedUserEntity(dto, foundTeacher.get());
        User updatedTeacher = this.userRepository.save(userEntity);

        return this.dtoMapper.toTeacherDTO(updatedTeacher);
    }

    @Override
    public void deleteUser(String id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public BaseUserDTO getUser(String id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            return user.get().getRole() == Role.STUDENT ?
                    (StudentDTO) this.dtoMapper.toStudentDTO(user.get()) :
                    (TeacherDTO) this.dtoMapper.toTeacherDTO(user.get());
        } else {
            throw new IllegalArgumentException("No user found with id: " + id);
        }
    }
}
