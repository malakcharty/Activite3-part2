package com.example.hospital.security.repo;

import com.example.hospital.security.entities.AppRole;
import com.example.hospital.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {
}