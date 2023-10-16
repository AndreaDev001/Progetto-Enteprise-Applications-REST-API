package com.enterpriseapplications.springboot.data.entities.interfaces;

import java.util.List;
import java.util.UUID;

public interface MultiOwnableEntity
{
    List<UUID> getOwners();
}
