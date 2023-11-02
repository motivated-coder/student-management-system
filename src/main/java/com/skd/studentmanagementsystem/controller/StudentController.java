package com.skd.studentmanagementsystem.controller;

import com.skd.studentmanagementsystem.dto.LoginCredentials;
import com.skd.studentmanagementsystem.model.Student;
import com.skd.studentmanagementsystem.service.AuthService;
import com.skd.studentmanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {

    private final StudentService studentService;
    private final AuthService authService;

    @PostMapping("/student/register")
    public ResponseEntity<?> register(@RequestBody Student student) throws Exception {
        Student student1 = authService.register(student);
        return ResponseEntity.ok(student1);
    }

    @PostMapping("/student/login")
    public String login(@RequestBody LoginCredentials loginCredentials){
        return authService.login(loginCredentials);
    }

    @GetMapping("/admin/student/all")
    public ResponseEntity<?> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/admin/student/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
}
