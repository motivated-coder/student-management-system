package com.skd.studentmanagementsystem.security;

import com.skd.studentmanagementsystem.model.Student;
import com.skd.studentmanagementsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("Invalid Username");
        });

        Set<SimpleGrantedAuthority> authorities = student.getRoles()
                .stream().map(r -> new SimpleGrantedAuthority(r.getRole()))
                .collect(Collectors.toSet());

        return User.builder()
                .username(username)
                .password(student.getPassword())
                .authorities(authorities)
                .build();
    }
}
