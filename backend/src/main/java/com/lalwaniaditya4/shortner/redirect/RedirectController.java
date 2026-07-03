package com.lalwaniaditya4.shortner.redirect;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
public class RedirectController {
    
    private final RedirectService redirectService;
    private final StringRedisTemplate stringRedisTemplate;

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String location = stringRedisTemplate.opsForValue().get("url:" + code);
        if(location == null)
        {
            location = redirectService.getLocation(code);

            if(location == null)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            stringRedisTemplate.opsForValue().set("url:" + code, location);
        }
        URI locationUri = URI.create(location);
        return ResponseEntity.status(HttpStatus.FOUND).location(locationUri).build();
    }
    
}
