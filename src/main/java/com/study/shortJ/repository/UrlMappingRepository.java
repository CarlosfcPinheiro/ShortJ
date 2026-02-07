package com.study.shortJ.repository;

import com.study.shortJ.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    @Query("SELECT u FROM tb_url_mapping u WHERE u.expirationDate > CURRENT_TIMESTAMP AND u.alias = :alias")
    Optional<UrlMapping> findByAlias(String alias);

    @Query(value = "SELECT NEXTVAL('url_mapping_seq')", nativeQuery = true)
    Long getNextSequentialId();

    void deleteByExpirationDateBefore(LocalDateTime date);
}
