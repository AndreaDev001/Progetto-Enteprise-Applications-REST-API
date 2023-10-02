package com.enterpriseapplications.springboot.data.dao;

import com.enterpriseapplications.springboot.data.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface CategoryDao extends JpaRepository<Category, UUID>
{

    @Query("select c from Category c where c.primaryCat = :requiredPrimaryCat and c.secondaryCat = :requiredSecondaryCat and c.tertiaryCat = :requiredTertiaryCat")
    Optional<Category> getCategory(@Param("requiredPrimaryCat") String primaryCat,@Param("requiredSecondaryCat") String secondaryCat,@Param("requiredTertiaryCat") String tertiaryCat);

    @Query("select c.primaryCat from Category c")
    List<String> getPrimaryCategories();
    @Query("select c.secondaryCat from Category c where c.primaryCat = :requiredPrimary")
    List<String> getCategoriesByPrimary(@Param("requiredPrimary") String primary);
    @Query("select c.tertiaryCat from Category c where c.primaryCat = :requiredPrimary and c.secondaryCat = :requiredSecondary")
    List<String> getCategoriesBySecondary(@Param("requiredPrimary") String primary,@Param("requiredSecondary") String secondary);
}
