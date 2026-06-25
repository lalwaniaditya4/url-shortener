package com.lalwaniaditya4.shortner.link;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkRepository extends JpaRepository<Link, String>{
    
    @Query("SELECT l.url AS url FROM Link l WHERE l.shortCode = :code")
    Optional<String> findLinkByCode(@Param("code") String code);
}
