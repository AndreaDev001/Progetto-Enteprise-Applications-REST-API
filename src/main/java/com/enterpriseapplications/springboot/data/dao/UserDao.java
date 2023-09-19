package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,Long> {
    @Query("select u from User u where u.username = :requiredUsername")
    Optional<User> getUserByUsername(@Param("requiredUsername") String username);
}
