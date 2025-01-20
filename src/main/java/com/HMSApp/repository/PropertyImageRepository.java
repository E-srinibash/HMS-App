package com.HMSApp.repository;

import com.HMSApp.entity.Property;
import com.HMSApp.entity.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
List<PropertyImage> findByProperty (Property property);
}