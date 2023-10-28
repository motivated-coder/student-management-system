package com.skd.studentmanagementsystem.repository;

import com.skd.studentmanagementsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(String role);
}
