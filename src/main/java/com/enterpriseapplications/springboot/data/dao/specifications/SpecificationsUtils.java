package com.enterpriseapplications.springboot.data.dao.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public abstract class SpecificationsUtils
{
   public static String likePattern(String value) {
       return "%" + value + "%";
   }
   public static Predicate generatePredicate(Predicate requiredPredicate, List<Predicate> requiredPredicates, CriteriaBuilder criteriaBuilder) {
       for(Predicate currentPredicate : requiredPredicates)
           requiredPredicate = criteriaBuilder.and(requiredPredicate,currentPredicate);
       return requiredPredicate;
   }
}
