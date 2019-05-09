package com.depromeet.deprocheck.deprocheckapi.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Session")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer id;
    /**
     * 세션 이름 (ex, 1주차)
     */
    private String name;
    /**
     * 주소 (ex, 서울시 강남구 논현로 22길)
     */
    private String address;
    /**
     * 위도, 경도
     */
    @Embedded
    private GeoLocation geoLocation;
    /**
     * 날짜
     */
    private ZonedDateTime date;
    /**
     * 시작 시간
     */
    @Column(name = "from_at")
    private ZonedDateTime fromAt;
    /**
     * 끝나는 시간
     */
    @Column(name = "to_at")
    private ZonedDateTime toAt;
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

    @OneToMany
    @JoinColumn(name = "attendance_id")
    private List<Attendance> attendances = new ArrayList<>();
}
