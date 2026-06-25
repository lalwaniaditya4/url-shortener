package com.lalwaniaditya4.shortner.link;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lalwaniaditya4.shortner.link.dto.CreateLinkRequest;
import com.lalwaniaditya4.shortner.link.dto.CreateLinkResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping("/")
    public ResponseEntity<CreateLinkResponse> postLink(@RequestBody CreateLinkRequest request) 
    {
        CreateLinkResponse response = linkService.createShortLink(request.originalUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}
