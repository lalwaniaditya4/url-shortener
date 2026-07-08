package com.lalwaniaditya4.shortner.link;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lalwaniaditya4.shortner.link.dto.CreateLinkResponse;
import com.lalwaniaditya4.shortner.link.exception.InvalidUrlException;
import com.lalwaniaditya4.shortner.link.exception.UnavailableShortCodeException;
import com.lalwaniaditya4.shortner.util.CodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final Set<String> RESERVED_CODES = Set.of("admin", "links", "api", "v1", "swagger-ui"); 
    
    public CreateLinkResponse createShortLink(String url, String customCode)
    {

        URI uri;

        try{
            uri = new URI(url);
            String scheme = uri.getScheme();

            if(scheme == null || !scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https")){
                throw new InvalidUrlException("Only HTTP and HTTPS links are allowed.");
            }

            if (uri.getHost() == null) {
                throw new InvalidUrlException("Host is required.");
            }
        }catch(URISyntaxException e){
            throw new InvalidUrlException("Invalid Url Format", e);
        }

        String linkCode;

        if(customCode == null)
        {
            linkCode = CodeGenerator.generate(8);
        }
        else {
            linkCode = customCode.toLowerCase();
            if(RESERVED_CODES.contains(linkCode))
            {
                throw new UnavailableShortCodeException("This code is already taken.");
            }
        }

        Link link = new Link();
        link.setShortCode(linkCode);
        link.setUrl(uri.toString());

        Link saved;
        if(linkRepository.existsById(linkCode))
        {
            throw new UnavailableShortCodeException("This code is already taken.");
        }
        else
        {
            saved = linkRepository.saveAndFlush(link);
            
        }

        stringRedisTemplate.opsForValue().set("url:" + linkCode, uri.toString());

        String shortUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/")
                    .path(saved.getShortCode())
                    .toUriString();

        return new CreateLinkResponse(shortUrl);
    }
}
