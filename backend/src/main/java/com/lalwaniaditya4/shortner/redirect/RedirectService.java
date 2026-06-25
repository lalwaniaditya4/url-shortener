package com.lalwaniaditya4.shortner.redirect;

import org.springframework.stereotype.Service;

import com.lalwaniaditya4.shortner.link.LinkRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedirectService {

    private final LinkRepository linkRepository;

    public String getLocation(String shortCode) {
        String url = linkRepository.findLinkByCode(shortCode).orElseThrow(() -> new EntityNotFoundException());
        return url;
    }
    
}
