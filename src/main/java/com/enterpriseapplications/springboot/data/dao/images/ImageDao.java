package com.enterpriseapplications.springboot.data.dao.images;


import com.enterpriseapplications.springboot.data.entities.enums.ImageType;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface ImageDao extends JpaRepository<Image, UUID>
{
    @Query("select i from Image i where i.type = :requiredType")
    List<Image> getImagesByType(@Param("requiredType") ImageType type);
}