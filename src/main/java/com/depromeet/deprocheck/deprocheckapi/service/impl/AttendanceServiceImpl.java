package com.depromeet.deprocheck.deprocheckapi.service.impl;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.exception.BadRequestException;
import com.depromeet.deprocheck.deprocheckapi.exception.ForbiddenException;
import com.depromeet.deprocheck.deprocheckapi.repository.AttendanceRepository;
import com.depromeet.deprocheck.deprocheckapi.service.AttendanceService;
import com.depromeet.deprocheck.deprocheckapi.service.MemberService;
import com.depromeet.deprocheck.deprocheckapi.service.SessionService;
import com.depromeet.deprocheck.deprocheckapi.utils.DistanceUtils;
import com.depromeet.deprocheck.deprocheckapi.vo.AttendanceValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    public static final int MAX_DISTANCE = 500;
    private final SessionService sessionService;
    private final MemberService memberService;
    private final AttendanceRepository attendanceRepository;

    @Override
    @Transactional
    public Attendance attend(AttendanceValue attendanceValue) {
        Assert.notNull(attendanceValue, "'attendanceValue' must not be null");

        // 세션 조회
        Session session = sessionService.getCurrentSession()
                .orElseThrow(() -> new BadRequestException("현재 출석할 수 있는 일정이 없습니다."));

        // 거리 검사
        double distance = DistanceUtils.getDistance(
                attendanceValue.getLatitude(),
                attendanceValue.getLongitude(),
                session.getLatitude(),
                session.getLongitude()
        );

        if (distance > MAX_DISTANCE) {
            throw new ForbiddenException("거리가 너무 멀어서 출석체크할 수 없습니다. ");
        }

        // 멤버 조회
        Integer memberId = attendanceValue.getMemberId();
        Member member = memberService.getMemberById(memberId);

        // 출석 정보 생성
        Attendance attendance = new Attendance();
        attendance.setSession(session);
        attendance.setMember(member);
        return attendanceRepository.save(attendance);
    }
}
