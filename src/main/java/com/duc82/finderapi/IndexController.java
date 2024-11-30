package com.duc82.finderapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class IndexController {
    @GetMapping()
    public ResponseEntity<Map<String,Object>> index() {
        Map<String,Object> response = Map.of(
                "message", "Hello World",
                "status",false
        );

        return ResponseEntity.ok(response);
    }
}
