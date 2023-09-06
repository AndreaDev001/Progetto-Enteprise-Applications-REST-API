package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Long> {

}
