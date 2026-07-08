package com.lalwaniaditya4.shortner.link.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateLinkRequest(
    @NotBlank
    String url,

    String customCode
){}
