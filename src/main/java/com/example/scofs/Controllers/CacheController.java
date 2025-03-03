package com.example.scofs.Controllers;

import com.example.scofs.Services.CacheableService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/cache")
public class CacheController {


    private final CacheableService cacheableService;

    public CacheController(CacheableService cacheableService) {
        this.cacheableService = cacheableService;
    }

    /**
     * This REST-End point handles the clearing of cache base on service name.
     *
     * @return - ResponseEntity<?>
     */
    @GetMapping(path = "/clear", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> clearCache(@RequestParam String connector) {
        Map<String, Object> responseMessage = new HashMap<>();
        cacheableService.evictCachedByClassName(connector);
        responseMessage.put("message", "Cache successfully evicted.");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}