package com.example.lc_demo.controller;

import com.example.lc_demo.dto.BaseUserDTO;
import com.example.lc_demo.dto.StudentDTO;
import com.example.lc_demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(final ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/students/count")
    public ResponseEntity<String> countStudents() {
        String responseMessage = "Count of students: " + this.reportService.countStudents();

        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/teachers/count")
    public ResponseEntity<String> countTeachers() {
        String responseMessage = "Count of teachers: " + this.reportService.countTeachers();

        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/courses/count-by-type")
    public ResponseEntity<String> countCoursesByType(@RequestParam String courseType) {
        String responseMessage = "Count of courses by " + courseType + " type: " +
                this.reportService.countCoursesByType(courseType);

        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/students/by-course/{courseId}")
    public ResponseEntity<List<StudentDTO>> studentsByCourse(@PathVariable Long courseId) {

        return ResponseEntity.ok(this.reportService.studentsByCourse(courseId));
    }

    @GetMapping("/students/by-group/{group}")
    public ResponseEntity<List<StudentDTO>> studentsByGroup(@PathVariable String group) {

        return ResponseEntity.ok(this.reportService.studentsByGroup(group));
    }

    @GetMapping("/users-by-group-and-course-id")
    public ResponseEntity<List<BaseUserDTO>> usersByGroupAndCourse(@RequestParam String group,
                                                                   @RequestParam Long courseId) {
        return ResponseEntity.ok(this.reportService.usersByGroupAndCourse(group, courseId));
    }

    @GetMapping("/students/older-than/{age}/course/{courseId}")
    public ResponseEntity<List<StudentDTO>> studentsOlderThanInCourse(@PathVariable Integer age,
                                                                      @PathVariable Long courseId) {
        return ResponseEntity.ok(this.reportService.studentsOlderThanInCourse(age, courseId));
    }
}

