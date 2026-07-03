package com.lalwaniaditya4.shortner.link;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lalwaniaditya4.shortner.link.dto.CreateLinkResponse;
import com.lalwaniaditya4.shortner.link.exception.InvalidUrlException;
import com.lalwaniaditya4.shortner.util.CodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final StringRedisTemplate stringRedisTemplate;
    
    public CreateLinkResponse createShortLink(String url)
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

        String linkCode = CodeGenerator.generate(8);

        Link link = new Link();
        link.setShortCode(linkCode);
        link.setUrl(uri.toString());

        Link saved = linkRepository.save(link);

        stringRedisTemplate.opsForValue().set("url:" + linkCode, uri.toString());

        String shortUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/")
                    .path(saved.getShortCode())
                    .toUriString();

        return new CreateLinkResponse(shortUrl);
    }
}
