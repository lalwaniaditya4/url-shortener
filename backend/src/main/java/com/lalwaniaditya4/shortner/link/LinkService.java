package com.lalwaniaditya4.shortner.link;

import org.springframework.stereotype.Service;

import com.lalwaniaditya4.shortner.link.dto.CreateLinkResponse;
import com.lalwaniaditya4.shortner.util.CodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    
    public CreateLinkResponse createShortLink(String url)
    {
        String linkCode = CodeGenerator.generate(8);

        Link link = new Link();
        link.setShortCode(linkCode);
        link.setUrl(url);

        Link saved = linkRepository.save(link);

        CreateLinkResponse response = new CreateLinkResponse(
            saved.getUrl(),
            saved.getShortCode()
        );

        return response;
    }
}
