package com.depromeet.deprocheck.deprocheckapi.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Integer id;

    /**
     * 이름
     */
    private String name;

    /**
     * 기수
     */
    private Integer termNumber;

    /**
     * 권한
     */
    @Enumerated(EnumType.STRING)
    private Authority authority;

    /**
     * 직군
     */
    @Enumerated(EnumType.STRING)
    private JobGroup jobGroup;

    /**
     * 생성 시간
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * 갱신 시간
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
