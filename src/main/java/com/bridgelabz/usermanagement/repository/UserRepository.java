package com.bridgelabz.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bridgelabz.usermanagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Object> {

	// Call create procedure from database for All time Age Group
	@Query(value = "call AgeGroup()", nativeQuery = true)
	List<Object[]> ageGroup();

	// call create procedure from DataBase for as per User Location
	@Query(value = "call Location()", nativeQuery = true)
	List<Object[]> location();

	// call create procedure from DataBase for as per User Location
	@Query(value = "call Gender()", nativeQuery = true)
	List<Object[]> gender();

	// find user by Email
	User findByEmail(String email);

	// find user by UserName
	User findByUserName(String userName);

	User findByGender(String gender);
}
