package com.duc82.finderapi.properties.dtos;


import com.duc82.finderapi.properties.entities.Property;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertiesResponse {
    private String message;
    private List<Property> properties;
    private int total;
    private int page;
    private int limit;
}
