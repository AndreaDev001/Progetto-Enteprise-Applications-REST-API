package com.enterpriseapplications.springboot.data.dao.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public abstract class BaseFilter {
    protected SpecificationsUtils.OrderMode orderMode = SpecificationsUtils.OrderMode.DESCENDED;
    protected List<UUID> excludedIDs = new ArrayList<>();
    public BaseFilter(SpecificationsUtils.OrderMode orderMode,List<UUID> excludedIDs) {
        this.orderMode = orderMode;
        this.excludedIDs = excludedIDs;
    }
}
