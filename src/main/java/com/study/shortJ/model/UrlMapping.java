package com.study.shortJ.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "tb_url_mapping")
public class UrlMapping {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="url_mapping_seq")
    @SequenceGenerator(name="url_mapping_seq", sequenceName="url_mapping_seq", allocationSize=1)
    private Long id;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "alias", nullable = false, unique = true)
    private String alias;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}
