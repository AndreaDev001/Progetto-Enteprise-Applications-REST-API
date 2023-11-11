package com.enterpriseapplications.springboot;

import com.enterpriseapplications.springboot.data.dao.CategoryDao;
import com.enterpriseapplications.springboot.data.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnterpriseApplicationsSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnterpriseApplicationsSpringBootApplication.class, args);
    }
}
