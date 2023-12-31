package com.enterpriseapplications.springboot.data.dao.specifications;

import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class UserSpecifications
{

    @Getter
    public enum OrderType
    {
        EMAIL("email"),
        USERNAME("username"),
        NAME("name"),
        SURNAME("surname"),
        DESCRIPTION("description"),
        RATING("rating"),
        CREATED_DATE("createdDate");

        private final String path;

        OrderType(String path) {
            this.path = path;
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Filter extends BaseFilter
    {
        private String email;
        private String username;
        private String name;
        private String surname;
        private String description;
        private Gender gender;
        private Integer minRating;
        private Integer maxRating;
        private List<OrderType> orderTypes;

        public Filter(SpecificationsUtils.OrderMode orderMode,User user) {
                super(orderMode,List.of(user.getId()));
                this.username = user.getUsername();
                if(user.getName() != null)
                    this.name = user.getName();
                if(user.getSurname() != null)
                    this.surname = user.getSurname();
                if(user.getRating() != null)
                    this.minRating = Math.toIntExact(user.getRating());
            this.gender = user.getGender();
        }
    }
    public static Specification<User> withFilter(Filter filter) {
        return (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> requiredPredicates = new ArrayList<>();
            List<Order> requiredOrders = new ArrayList<>();

            if(filter.email != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("email"),SpecificationsUtils.likePattern(filter.email)));
            if(filter.username != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("username"),SpecificationsUtils.likePattern(filter.username)));
            if(filter.name != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("name"),SpecificationsUtils.likePattern(filter.name)));
            if(filter.surname != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("surname"),SpecificationsUtils.likePattern(filter.surname)));
            if(filter.description != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("description"),SpecificationsUtils.likePattern(filter.description)));
            if(filter.gender != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("gender"),filter.gender));
            if(filter.minRating != null && filter.minRating > 0)
                requiredPredicates.add(criteriaBuilder.ge(root.get("rating"),filter.minRating));
            if(filter.maxRating != null  && filter.maxRating > 0)
                requiredPredicates.add(criteriaBuilder.le(root.get("rating"),filter.maxRating));
            if(filter.orderTypes == null || filter.orderTypes.isEmpty())
                filter.orderTypes = List.of(OrderType.CREATED_DATE);
            if(filter.orderMode == null)
                filter.orderMode = SpecificationsUtils.OrderMode.DESCENDED;

            Predicate requiredPredicate = SpecificationsUtils.generateExcludedPredicate(root,criteriaBuilder,filter.excludedIDs);
            requiredPredicate = criteriaBuilder.and(requiredPredicate,criteriaBuilder.equal(root.get("visibility"), UserVisibility.PUBLIC));
            requiredPredicate = SpecificationsUtils.generatePredicate(requiredPredicate,requiredPredicates,criteriaBuilder);
            requiredOrders = SpecificationsUtils.generateOrders(root,criteriaBuilder,filter.orderTypes,filter.orderMode);
            return criteriaQuery.where(requiredPredicate).orderBy(requiredOrders).getRestriction();
        };
    }
}
