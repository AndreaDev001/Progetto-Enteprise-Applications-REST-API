package com.enterpriseapplications.springboot.data.dao.specifications;

import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications
{
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Filter
    {
        private String primaryCat;
        private String secondaryCat;
        private String tertiaryCat;
        private String name;
        private String description;
        private ProductCondition condition;
        private Long minPrice;
        private Long maxPrice;
    }

    public static Specification<Product> withFilter(Filter filter) {
        return (Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> requiredPredicates = new ArrayList<>();
            if(filter.primaryCat != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("category").get("primaryCat"),filter.primaryCat));
            if(filter.secondaryCat != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("category").get("secondaryCat"),filter.secondaryCat));
            if(filter.tertiaryCat != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("category").get("tertiaryCat"),filter.tertiaryCat));
            if(filter.name != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("name"),SpecificationsUtils.likePattern(filter.name)));
            if(filter.description != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("description"),SpecificationsUtils.likePattern(filter.description)));
            if(filter.condition != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("condition"),filter.condition));
            if(filter.minPrice != null && filter.minPrice > 0)
                requiredPredicates.add(criteriaBuilder.ge(root.get("price"),filter.minPrice));
            if(filter.maxPrice != null && filter.maxPrice > 0)
                requiredPredicates.add(criteriaBuilder.le(root.get("price"),filter.maxPrice));

            Predicate requiredPredicate = criteriaBuilder.isNotNull(root.get("id"));
            requiredPredicate = criteriaBuilder.and(requiredPredicate,criteriaBuilder.equal(root.get("visibility"), ProductVisibility.PUBLIC));
            requiredPredicate = SpecificationsUtils.generatePredicate(requiredPredicate,requiredPredicates,criteriaBuilder);
            return criteriaQuery.where(requiredPredicate).getRestriction();
        };
    }
}
