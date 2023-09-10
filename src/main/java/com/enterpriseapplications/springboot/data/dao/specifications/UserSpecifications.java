package com.enterpriseapplications.springboot.data.dao.specifications;

import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class UserSpecifications
{
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Filter
    {
        private String email;
        private String username;
        private String name;
        private String surname;
        private String description;
        private Integer minRating;
        private Integer maxRating;
    }
    public static Specification<User> withFilter(Filter filter) {
        return (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> requiredPredicates = new ArrayList<>();
            if(filter.email != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("email"),SpecificationsUtils.likePattern(filter.email)));
            if(filter.username != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("username"),SpecificationsUtils.likePattern(filter.username)));
            if(filter.name != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("name"),SpecificationsUtils.likePattern(filter.name)));
            if(filter.surname != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("surname"),SpecificationsUtils.likePattern(filter.surname)));
            if(filter.description != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("email"),SpecificationsUtils.likePattern(filter.email)));
            if(filter.minRating != null && filter.minRating > 0)
                requiredPredicates.add(criteriaBuilder.ge(root.get("rating"),filter.minRating));
            if(filter.maxRating != null  && filter.maxRating > 0)
                requiredPredicates.add(criteriaBuilder.le(root.get("rating"),filter.maxRating));

            Predicate requiredPredicate = criteriaBuilder.isNotNull(root.get("id"));
            requiredPredicate = criteriaBuilder.and(requiredPredicate,criteriaBuilder.equal(root.get("visibility"), UserVisibility.PUBLIC));
            requiredPredicate = SpecificationsUtils.generatePredicate(requiredPredicate,requiredPredicates,criteriaBuilder);
            return criteriaQuery.where(requiredPredicate).getRestriction();
        };
    }
}
