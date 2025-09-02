package com.example.lc_demo.controller;

import com.example.lc_demo.dto.StudentDTO;
import com.example.lc_demo.dto.TeacherDTO;
import com.example.lc_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String id) {

        return ResponseEntity.ok((StudentDTO) this.userService.getUser(id));
    }

    @PostMapping("/student")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO dto) {

        StudentDTO student = this.userService.createStudent(dto);

        return ResponseEntity.ok(student);
    }

    @PutMapping("/student")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO dto) {
        StudentDTO student = this.userService.updateStudent(dto);

        return ResponseEntity.ok(student);
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable String id) {

        return ResponseEntity.ok((TeacherDTO) this.userService.getUser(id));
    }

    @PostMapping("/teacher")
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO dto) {

        TeacherDTO teacher = this.userService.createTeacher(dto);

        return ResponseEntity.ok(teacher);
    }

    @PutMapping("/teacher")
    public ResponseEntity<TeacherDTO> updateTeacher(@RequestBody TeacherDTO dto) {
        TeacherDTO teacher = this.userService.updateTeacher(dto);

        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        this.userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
