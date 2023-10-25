package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dao.LikeDao;
import com.enterpriseapplications.springboot.data.dto.output.LikeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface LikeService
{
    PagedModel<LikeDto> getLikes(Pageable pageable);
    PagedModel<LikeDto> getLikesByUser(UUID userID, Pageable pageable);
    PagedModel<LikeDto> getLikesByProduct(UUID productID,Pageable pageable);
    LikeDto getLike(UUID likeID);
    LikeDto getLike(UUID userID,UUID productID);
    LikeDto createLike(UUID productID);
    void deleteLike(UUID likeID);
    void deleteLikeByProduct(UUID productID);
}
