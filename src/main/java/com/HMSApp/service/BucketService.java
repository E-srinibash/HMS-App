package com.HMSApp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class BucketService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Set the metadata for the file (optional)
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        // Upload the file
        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(bucketName, fileName, inputStream, metadata);
        }

        // Generate the file URL
        return s3Client.getUrl(bucketName, fileName).toString();
    }
}