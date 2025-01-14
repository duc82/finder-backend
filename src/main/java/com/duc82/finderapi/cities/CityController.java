package com.duc82.finderapi.cities;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cities")
@AllArgsConstructor
public class CityController {
    @GetMapping()
    public String getAll() {
        return "All cities";
    }
}
