package com.duc82.finderapi.properties;

import com.duc82.finderapi.properties.dtos.PropertyRequest;
import com.duc82.finderapi.properties.dtos.PropertyResponse;
import com.duc82.finderapi.properties.entities.Property;
import com.duc82.finderapi.properties.entities.PropertyImage;
import com.duc82.finderapi.properties.enums.PropertyTransactionType;
import com.duc82.finderapi.properties.enums.PropertyType;
import com.duc82.finderapi.users.UserService;
import com.duc82.finderapi.users.entities.User;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final UserService userService;
    public static String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";

    public PropertyResponse create(PropertyRequest propertyRequest, String email, MultipartFile[] files) {
        User user = userService.findByEmail(email);

        List<PropertyImage> images = new ArrayList<>();

        for (MultipartFile file : files) {
            Path path = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
            try {
                file.transferTo(path);
                String filePath = path.toString();
                PropertyImage image = new PropertyImage();
                image.setUrl(filePath);
                images.add(image);
            } catch (Exception e) {
                throw new RuntimeException("Failed to save file");
            }
        }

        Property property = new Property();
        property.setDescription(propertyRequest.getDescription());
        property.setAddress(propertyRequest.getAddress());
        property.setLatitude(propertyRequest.getLatitude());
        property.setLongitude(propertyRequest.getLongitude());

        PropertyType type = PropertyType.valueOf(propertyRequest.getType());
        property.setType(type);

        property.setPrice(propertyRequest.getPrice());
        property.setArea(propertyRequest.getArea());
        property.setBedrooms(propertyRequest.getBedrooms());
        property.setBathrooms(propertyRequest.getBathrooms());
        property.setGarages(propertyRequest.getGarages());
        property.setFloors(propertyRequest.getFloors());

        PropertyTransactionType transactionType = PropertyTransactionType.valueOf(propertyRequest.getTransactionType());
        property.setTransactionType(transactionType);

        property.setVerified(propertyRequest.isVerified());
        property.setAmenities(propertyRequest.getAmenities());
        property.setUser(user);

        property.setImages(images);

        propertyRepository.save(property);

        return new PropertyResponse("Property created successfully");
    }
}
