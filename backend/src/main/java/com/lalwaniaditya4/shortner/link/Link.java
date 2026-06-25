package com.lalwaniaditya4.shortner.link;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity()
@Table(name = "links")
@NoArgsConstructor
@Getter
@Setter
public class Link {
    
    @Id
    private String shortCode;

    private String url;
}
