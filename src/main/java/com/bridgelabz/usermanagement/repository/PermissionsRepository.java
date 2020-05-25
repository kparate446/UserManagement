package com.bridgelabz.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.usermanagement.model.Permissions;
@Repository
public interface PermissionsRepository extends JpaRepository<Permissions,Object>{

}
