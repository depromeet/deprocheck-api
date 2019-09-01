package com.depromeet.deprocheck.deprocheckapi.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

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

    public static Member create(String name, Integer termNumber, JobGroup jobGroup, Authority authority) {
        Member member = new Member();
        member.name = name;
        member.termNumber = termNumber;
        member.jobGroup = jobGroup;
        member.authority = authority;
        member.validate();
        return member;
    }

    private void validate() {
        if (StringUtils.isEmpty(this.name)) {
            throw new IllegalArgumentException("'name' must not be null or empty");
        }
        if (this.termNumber < 0) {
            throw new IllegalArgumentException("'termNumber' must be greater than or equal to zero");
        }
        if (this.jobGroup == null || this.jobGroup == JobGroup.UNKNOWN) {
            throw new IllegalArgumentException("'jobGroup' must not be null or UNKNOWN");
        }
        if (this.authority == null) {
            throw new IllegalArgumentException("'authority' must not be null");
        }
    }
}
