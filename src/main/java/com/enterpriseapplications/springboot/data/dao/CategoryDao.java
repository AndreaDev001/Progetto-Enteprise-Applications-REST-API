package com.enterpriseapplications.springboot.data.dao;

import com.enterpriseapplications.springboot.data.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryDao extends JpaRepository<Category,Long>
{
    @Query("select c.primary from Category c")
    List<String> getCategories();
    @Query("select c.secondary from Category c where c.primary = :requiredPrimary")
    List<String> getCategoriesByPrimary(@Param("requiredPrimary") String primary);
    @Query("select c.tertiary from Category c where c.primary = :requiredPrimary and c.secondary = :requiredSecondary")
    List<String> getCategoriesBySecondary(@Param("requiredPrimary") String primary,@Param("requiredSecondary") String secondary);
}
