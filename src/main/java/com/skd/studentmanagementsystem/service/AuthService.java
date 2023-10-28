package com.skd.studentmanagementsystem.service;

import com.skd.studentmanagementsystem.dto.LoginCredentials;
import com.skd.studentmanagementsystem.model.Role;
import com.skd.studentmanagementsystem.model.Student;
import com.skd.studentmanagementsystem.repository.RoleRepository;
import com.skd.studentmanagementsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public Student register(Student student) throws Exception {
        if(studentRepository.existsByEmail(student.getEmail()))
            throw new Exception("Email already exists");
        if(studentRepository.existsByUsername(student.getUsername()))
            throw new Exception("Username already exists");

        Set<Role> roles = new HashSet<>();

        if(Objects.isNull(student.getRoles()) || student.getRoles().isEmpty()){
            student.setRoles(Set.of(roleRepository.findById(2).orElse(Role.builder().role("ROLE_External").build())));
        }
        else{
            student.getRoles().stream().forEach(r -> {
                 Role role = roleRepository.findByRole(r.getRole()).orElseThrow(() -> new RuntimeException("Invalid Roles provided"));
                 roles.add(role);
            });
            student.setRoles(roles);
        }

        student.setPassword(passwordEncoder.encode(student.getPassword()));

        return studentRepository.save(student);
    }

    public String login(LoginCredentials loginCredentials){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(),
                        loginCredentials.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "Login Successful";
    }
}
