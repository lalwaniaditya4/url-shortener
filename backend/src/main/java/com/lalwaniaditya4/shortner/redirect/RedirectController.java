package com.lalwaniaditya4.shortner.redirect;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
public class RedirectController {
    
    private final RedirectService redirectService;

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String location = redirectService.getLocation(code);
        URI locationUri = URI.create(location);
        return ResponseEntity.status(HttpStatus.FOUND).location(locationUri).build();
    }
    
}
