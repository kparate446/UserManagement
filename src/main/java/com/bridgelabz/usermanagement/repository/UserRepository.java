package com.bridgelabz.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bridgelabz.usermanagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Object> {
	
	// Call create procedure from database for All time Age Group
	@Query(value = "call AgeGroup()", nativeQuery = true)
	List<Object[]> ageGroup();
	
	
	// find user by Email
	User findByEmail(String email);
	//find user by UserName
	User findByUserName(String userName);
	User findByGender(String gender);
}
