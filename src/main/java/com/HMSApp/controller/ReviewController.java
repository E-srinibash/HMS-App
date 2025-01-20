package com.HMSApp.controller;

import com.HMSApp.entity.Property;
import com.HMSApp.entity.Reviews;
import com.HMSApp.entity.User;
import com.HMSApp.repository.PropertyRepository;
import com.HMSApp.repository.ReviewsRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private ReviewsRepository reviewsRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewsRepository reviewsRepository, PropertyRepository propertyRepository) {
        this.reviewsRepository = reviewsRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping
    public String addReview(
            @RequestBody Reviews review,
            @RequestParam long propertyId,
            @AuthenticationPrincipal User user
            ){
        Property property =  propertyRepository.findById(propertyId).get();
        Reviews reviews = reviewsRepository.findByPropertyAndUser(property,user);

        if(reviews == null){
        review.setProperty(property);
        review.setUser(user);
        reviewsRepository.save(review);
        return "added..";
        }
        return "Review already given";
    }


    @GetMapping("/user/reviews")
    public List<Reviews> viewMyReviews(
            @AuthenticationPrincipal User user
    ){
        return reviewsRepository.findByUser(user);
    }
}
