package com.HMSApp.controller;

import com.HMSApp.entity.Property;
import com.HMSApp.entity.PropertyImage;
import com.HMSApp.repository.PropertyImageRepository;
import com.HMSApp.repository.PropertyRepository;
import com.HMSApp.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/property")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private PropertyImageRepository propertyImageRepository;

    @PostMapping("/addProperty")
    public String addProperty() {
        // Add property logic
        return "Property added";
    }

    @DeleteMapping("/deleteProperty")
    public String deleteProperty(){
        return "Propery deleted";
    }

    @GetMapping("/{searchParam}")
    public List<Property> searchProperty(
            @PathVariable String searchParam
     ){
        return propertyRepository.searchProperty(searchParam);
    }

    @PostMapping("/upload/file/property/{propertyId}")
    public String uploadPropertyPhotos(
            @RequestParam MultipartFile file,
            @PathVariable long propertyId
    ) throws IOException {
        String imageUrl = bucketService.uploadImage(file);
        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setUrl(imageUrl);

        // SET FK

        Optional<Property> property = propertyRepository.findById(propertyId);
        property.ifPresent(propertyImage::setProperty);
        propertyImageRepository.save(propertyImage);
        return "Image uploaded";
    }

    @GetMapping("/get/property/images")
    public List<PropertyImage> getPropertyImages(
            @RequestParam Long id
    ){
       Property property = propertyRepository.findById(id).get();
       return propertyImageRepository.findByProperty(property);
    }
}
