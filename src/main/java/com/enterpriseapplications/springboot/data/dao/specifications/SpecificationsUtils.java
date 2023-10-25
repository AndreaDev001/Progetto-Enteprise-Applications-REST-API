package com.enterpriseapplications.springboot.data.dao.specifications;

import jakarta.persistence.criteria.*;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class SpecificationsUtils
{
    public enum OrderMode
    {
        DESCENDED,
        ASCENDED
    }

   public static String likePattern(String value) {
       return "%" + value + "%";
   }


   public static Predicate generatePredicate(Predicate requiredPredicate, List<Predicate> requiredPredicates, CriteriaBuilder criteriaBuilder) {
       for(Predicate currentPredicate : requiredPredicates)
           requiredPredicate = criteriaBuilder.and(requiredPredicate,currentPredicate);
       return requiredPredicate;
   }

   public static<T> Predicate generateExcludedPredicate(Root<T> root,CriteriaBuilder criteriaBuilder,List<UUID> values) {
        Predicate predicate = criteriaBuilder.isNotNull(root.get("id"));
        for(UUID value : values)
            predicate = criteriaBuilder.and(predicate,criteriaBuilder.notEqual(root.get("id"),value));
        return predicate;
   }

   @SneakyThrows
   public static<T> List<Order> generateOrders(Root<T> root, CriteriaBuilder criteriaBuilder, List<? extends Enum<?>> paths, OrderMode orderMode) {
        if(orderMode == null)
            orderMode = OrderMode.DESCENDED;
        if(paths == null || paths.isEmpty())
            return null;
        List<Order> requiredOrders = new ArrayList<>();
        boolean desc = orderMode == OrderMode.DESCENDED;
        for(Object current : paths) {
            Field requiredField = current.getClass().getDeclaredField("path");
            requiredField.setAccessible(true);
            String requiredPath = (String)requiredField.get(current);
            String[] values = requiredPath.split("\\.");
            Path<?> currentPath = root.get(values[0]);
            if(values.length > 1) {
                for(int i = 1;i < values.length;i++) {
                    String value = values[i];
                    currentPath = currentPath.get(value);
                }
            }
            Order requiredOrder =  desc ? criteriaBuilder.desc(currentPath) : criteriaBuilder.asc(currentPath);
            requiredOrders.add(requiredOrder);
        }
        return requiredOrders;
   }
}
