package com.enterpriseapplications.springboot.data.dao.specifications;

import com.enterpriseapplications.springboot.data.entities.Offer;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
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

public class OfferSpecifications
{
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Filter
    {
        private String description;
        private Long price;
        private String buyerEmail;
        private String buyerUsername;
        private String productName;
        private String productDescription;
        private OfferStatus status;
        private Boolean expired;
    }
    public static Specification<Offer> withFilter(Filter filter) {
        return (Root<Offer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> requiredPredicates = new ArrayList<>();
            if(filter.description != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("description"),SpecificationsUtils.likePattern(filter.description)));
            if(filter.price != null && filter.price > 0)
                requiredPredicates.add(criteriaBuilder.ge(root.get("price"),filter.price));
            if(filter.buyerEmail != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("buyer").get("email"),SpecificationsUtils.likePattern(filter.buyerEmail)));
            if(filter.buyerUsername != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("buyer").get("username"),SpecificationsUtils.likePattern(filter.buyerUsername)));
            if(filter.productName != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("product").get("name"),SpecificationsUtils.likePattern(filter.productName)));
            if(filter.productDescription != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("product").get("description"),SpecificationsUtils.likePattern(filter.productDescription)));
            if(filter.status != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("status"),filter.status));
            if(filter.expired != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("expired"),filter.expired));

            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(criteriaBuilder.isNotNull(root.get("id")),requiredPredicates,criteriaBuilder);
            return criteriaQuery.where(requiredPredicate).getRestriction();
        };
    }
}
