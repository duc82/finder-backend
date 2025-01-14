package com.duc82.finderapi.properties.dtos;


import com.duc82.finderapi.properties.enums.PropertyStatus;
import com.duc82.finderapi.properties.enums.PropertyTransactionType;
import com.duc82.finderapi.properties.enums.PropertyType;
import com.duc82.finderapi.validators.EnumValidator;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequest {
    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message="Latitude is required")
    @Digits(integer = 10, fraction = 8)
    private BigDecimal latitude;

    @NotBlank(message="Longitude is required")
    @Digits(integer = 11, fraction = 8)
    private BigDecimal longitude;

    @EnumValidator(enumClass = PropertyType.class, message = "Invalid property type")
    private String type;

    @NotBlank(message = "Price is required")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal price;

    @NotBlank(message = "Area is required")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal area;

    @NotBlank(message = "Bedrooms is required")
    private int bedrooms;

    @NotBlank(message = "Bathrooms is required")
    private int bathrooms;

    @NotBlank(message = "Garages is required")
    private int garages;

    @NotBlank(message = "Floors is required")
    private int floors;

    @EnumValidator(enumClass = PropertyTransactionType.class, message = "Invalid transaction type")
    private String transactionType;

    private boolean verified;

    private String amenities;
}
