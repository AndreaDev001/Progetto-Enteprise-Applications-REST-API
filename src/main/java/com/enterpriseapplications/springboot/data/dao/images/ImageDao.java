package com.enterpriseapplications.springboot.data.dao.images;


import com.enterpriseapplications.springboot.data.entities.images.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ImageDao extends JpaRepository<Image,Long>
{
    @Query("select i from Image i where i.type = :requiredType")
    List<Image> getImagesByType(@Param("requiredType") String type);
    @Query("select i from Image i where i.name = :requiredName")
    List<Image> getImagesByName(@Param("requiredName") String name);
}