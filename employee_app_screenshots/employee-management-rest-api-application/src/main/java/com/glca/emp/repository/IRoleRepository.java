package com.glca.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.glca.emp.entity.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

}
