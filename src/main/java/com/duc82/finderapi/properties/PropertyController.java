package com.duc82.finderapi.properties;

import com.duc82.finderapi.properties.dtos.PropertyRequest;
import com.duc82.finderapi.properties.dtos.PropertyResponse;
import com.duc82.finderapi.properties.entities.Property;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/properties")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;
    private final PropertyRepository propertyRepository;

    @GetMapping()
    public Page<Property> getAll(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) BigDecimal minArea,
            @RequestParam(required = false) BigDecimal maxArea,
            @RequestParam(required = false) int bedrooms,
            @RequestParam(required = false) int bathrooms,
            @RequestParam(required = false) int minYear,
            @RequestParam(required = false) int maxYear,
            @RequestParam(required = false) boolean verified,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sort[1]), sort[0]));

        Specification<Property> spec = Specification.where(PropertySpecification.hasAddress(address))
                .and(PropertySpecification.hasTransactionType(transactionType))
                .and(PropertySpecification.hasType(type))
                .and(PropertySpecification.hasMinPrice(minPrice))
                .and(PropertySpecification.hasMaxPrice(maxPrice))
                .and(PropertySpecification.hasMinArea(minArea))
                .and(PropertySpecification.hasMaxArea(maxArea))
                .and(PropertySpecification.hasBedrooms(bedrooms))
                .and(PropertySpecification.hasBathrooms(bathrooms))
                .and(PropertySpecification.hasMinYear(minYear))
                .and(PropertySpecification.hasMaxYear(maxYear))
                .and(PropertySpecification.hasVerified(verified));

        return propertyRepository.findAllByDeletedAtIsNull(spec, pageable);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyResponse create(
           @Valid @RequestBody PropertyRequest propertyRequest,
           Authentication authentication,
           @RequestParam("images") MultipartFile[] files
    ) {
        return propertyService.create(propertyRequest, authentication.getName(), files);
    }
}
