package com.identityservice.repository;

import com.identityservice.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

    boolean existsByName(String name);

    Optional<Permission> findByName(String name);

    void deleteByName(String name);
}
