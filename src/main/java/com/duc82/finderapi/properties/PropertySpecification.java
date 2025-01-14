package com.duc82.finderapi.properties;

import com.duc82.finderapi.properties.entities.Property;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class PropertySpecification {
    public static Specification<Property> hasAddress(String address) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("address"), "%" + address + "%");
    }

    public static Specification<Property> hasTransactionType(String transactionType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("transactionType"), transactionType);
    }

    public static Specification<Property> hasType(String type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Property> hasMinPrice(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Property> hasMaxPrice(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Property> hasMinArea(BigDecimal minArea) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("area"), minArea);
    }

    public static Specification<Property> hasMaxArea(BigDecimal maxArea) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("area"), maxArea);
    }

    public static Specification<Property> hasBedrooms(int bedrooms) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bedrooms"), bedrooms);
    }

    public static Specification<Property> hasBathrooms(int bathrooms) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bathrooms"), bathrooms);
    }

    public static Specification<Property> hasMinYear(int minYear) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("year"), minYear);
    }

    public static Specification<Property> hasMaxYear(int maxYear) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("year"), maxYear);
    }

    public static Specification<Property> hasVerified(boolean verified) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("verified"), verified);
    }
}
