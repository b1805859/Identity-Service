package com.identityservice.repository;


import com.identityservice.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  boolean existsByName(String name);

  Optional<Role> findByName(String name);
}
