package com.depromeet.deprocheck.deprocheckapi.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Member")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer id;

    /**
     * 이름
     */
    private String name;

    /**
     * 기수
     */
    @Column(name = "term_number")
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
    @Column(name = "job_group")
    private JobGroup jobGroup;

    /**
     * 생성 시간
     */
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 갱신 시간
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
