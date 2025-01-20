package com.HMSApp.repository;

import com.HMSApp.entity.Property;
import com.HMSApp.entity.Reviews;
import com.HMSApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
  List<Reviews> findByUser(User user);
   Reviews  findByPropertyAndUser(Property property,User user);
}