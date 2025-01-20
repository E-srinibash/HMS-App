package com.HMSApp.repository;

import com.HMSApp.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("Select p from Property p join p.city c join p.country co where c.name=:searchParam or co.name=:searchParam")
    List<Property> searchProperty (@Param("searchParam") String searchParam);
}