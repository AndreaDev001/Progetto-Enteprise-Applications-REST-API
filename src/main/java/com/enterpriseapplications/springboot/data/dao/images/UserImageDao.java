package com.enterpriseapplications.springboot.data.dao.images;


import com.enterpriseapplications.springboot.data.entities.images.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserImageDao extends JpaRepository<UserImage,Long>
{
    @Query("select u from UserImage u where u.user.id = :requiredID")
    Optional<UserImage> getUserImage(@Param("requiredID") Long requiredID);
}
