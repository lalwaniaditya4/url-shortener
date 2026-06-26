package com.lalwaniaditya4.shortner.redirect;

import org.springframework.stereotype.Service;

import com.lalwaniaditya4.shortner.link.LinkRepository;
import com.lalwaniaditya4.shortner.redirect.exception.InvalidShortCodeException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedirectService {

    private final LinkRepository linkRepository;

    public String getLocation(String shortCode) {

        try{
            return linkRepository.findLinkByCode(shortCode).orElseThrow(() -> new EntityNotFoundException());
            
        }catch(EntityNotFoundException e){
            throw new InvalidShortCodeException("Short code is invalid or expired.");
        }
    }
    
}
