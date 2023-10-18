package com.enterpriseapplications.springboot.data.dao.specifications;

import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BanSpecifications
{
    @Getter
    public enum OrderType
    {
        BANNER_USERNAME("banner.username"),
        BANNED_USERNAME("banned.username"),
        BANNER_EMAIL("banner.email"),
        BANNED_EMAIL("banned.email"),
        BANNER_NAME("banner.name"),
        BANNED_NAME("banned.name"),
        BANNER_SURNAME("banner.surname"),
        BANNED_SURNAME("banned.surname"),
        DESCRIPTION("description"),
        CREATED_DATE("createdDate");

        private final String path;
        OrderType(String path) {
            this.path = path;
        }
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Filter
    {
        private String bannerEmail;
        private String bannedEmail;
        private String bannerUsername;
        private String bannedUsername;
        private String description;
        private ReportReason reason;
        private Boolean expired;
        private List<OrderType> orderTypes;
        private SpecificationsUtils.OrderMode orderMode;

        public Filter(Ban ban) {
            this.bannerEmail = ban.getBanner().getEmail();
            this.bannedEmail = ban.getBanned().getEmail();
            this.bannedUsername = ban.getBanned().getUsername();
            this.bannerUsername = ban.getBanner().getUsername();
            this.reason = ban.getReason();
            this.expired = ban.isExpired();
        }
    }

    public static Specification<Ban> withFilter(Filter filter) {
        return (Root<Ban> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> requiredPredicates = new ArrayList<>();
            List<Order> requiredOrders = new ArrayList<>();

            if(filter.bannerEmail != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("banner").get("email"),filter.bannerEmail));
            if(filter.bannedEmail != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("banned").get("email"),filter.bannedEmail));
            if(filter.bannerUsername != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("banner").get("username"),filter.bannerUsername));
            if(filter.bannedUsername != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("banned").get("username"),filter.bannedUsername));
            if(filter.description != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("description"),filter.description));
            if(filter.reason != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("reason"),filter.reason));
            if(filter.expired != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("expired"),filter.expired));
            if(filter.orderTypes == null)
                filter.orderTypes = List.of(OrderType.CREATED_DATE);

            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(criteriaBuilder.isNotNull(root.get("id")),requiredPredicates,criteriaBuilder);
            requiredOrders = SpecificationsUtils.generateOrders(root,criteriaBuilder,filter.getOrderTypes(),filter.orderMode);
            return criteriaQuery.where(requiredPredicate).orderBy(requiredOrders).getRestriction();
        };
    }
}
