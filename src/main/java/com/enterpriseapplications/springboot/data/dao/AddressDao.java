package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;


@Repository
public interface AddressDao extends JpaRepository<Address, UUID>
{
    @Query("select a from Address a where a.user.id = :requiredID")
    List<Address> getAddressesByUser(@Param("requiredID") UUID id);
    @Query("select a from Address a where a.ownerName = :requiredName")
    Page<Address> getAddressesByOwnerName(@Param("requiredName") String name, Pageable pageable);
}
