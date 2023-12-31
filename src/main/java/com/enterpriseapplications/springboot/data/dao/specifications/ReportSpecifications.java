package com.enterpriseapplications.springboot.data.dao.specifications;

import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportSpecifications
{

    @Getter
    public enum OrderType
    {
        REPORTER_USERNAME("reporter.username"),
        REPORTED_USERNAME("reported.username"),
        REPORTER_NAME("reporter.name"),
        REPORTED_NAME("reported.name"),
        REPORTER_SURNAME("reporter.surname"),
        REPORTED_SURNAME("reported.surname"),
        REPORTER_EMAIL("reporter.email"),
        REPORTED_EMAIL("reported.email"),
        DESCRIPTION("description"),
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
        private String reporterEmail;
        private String reportedEmail;
        private String reporterUsername;
        private String reportedUsername;
        private String description;
        private ReportReason reason;
        private ReportType type;
        private List<OrderType> orderTypes;

        public Filter(SpecificationsUtils.OrderMode orderMode,Report report) {
            super(orderMode,List.of(report.getId()));
            this.reportedEmail = report.getReported().getEmail();
            this.reporterEmail = report.getReporter().getEmail();
            this.reporterUsername = report.getReporter().getUsername();
            this.reportedUsername = report.getReported().getUsername();
            this.description = report.getDescription();
            this.reason = report.getReason();
        }
    }
    public static Specification<Report> withFilter(Filter filter) {
        return (Root<Report> root, CriteriaQuery<?> criteriaQuery,CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> requiredPredicates = new ArrayList<>();
            List<Order> requiredOrders = new ArrayList<>();

            if(filter.reporterEmail != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("reporter").get("email"),SpecificationsUtils.likePattern(filter.reporterEmail)));
            if(filter.reportedEmail != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("reported").get("username"),SpecificationsUtils.likePattern(filter.reportedEmail)));
            if(filter.reporterUsername != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("reporter").get("username"),SpecificationsUtils.likePattern(filter.reporterUsername)));
            if(filter.reportedUsername != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("reported").get("username"),SpecificationsUtils.likePattern(filter.reportedUsername)));
            if(filter.description != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("description"),SpecificationsUtils.likePattern(filter.description)));
            if(filter.reason != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("reason"),filter.reason));
            if(filter.type != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("type"),filter.type));
            if(filter.orderTypes == null)
                filter.orderTypes = List.of(OrderType.CREATED_DATE);

            Predicate requiredPredicate = SpecificationsUtils.generateExcludedPredicate(root,criteriaBuilder,filter.excludedIDs);
            requiredPredicate = SpecificationsUtils.generatePredicate(requiredPredicate,requiredPredicates,criteriaBuilder);
            requiredOrders = SpecificationsUtils.generateOrders(root,criteriaBuilder,filter.getOrderTypes(),filter.orderMode);
            return criteriaQuery.where(requiredPredicate).orderBy(requiredOrders).getRestriction();
        };
    }
}
