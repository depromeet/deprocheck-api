package com.depromeet.deprocheck.deprocheckapi.controller;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.dto.MemberAttendRequest;
import com.depromeet.deprocheck.deprocheckapi.dto.member.MemberAttendanceResponse;
import com.depromeet.deprocheck.deprocheckapi.exception.BadRequestException;
import com.depromeet.deprocheck.deprocheckapi.service.AttendanceService;
import com.depromeet.deprocheck.deprocheckapi.service.MemberService;
import com.depromeet.deprocheck.deprocheckapi.service.SessionService;
import com.depromeet.deprocheck.deprocheckapi.vo.AttendanceValue;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final AttendanceService attendanceService;
    private final MemberService memberService;
    private final SessionService sessionService;

    /**
     * 출석 체크를 요청합니다
     * - 성공하는 경우 200
     * - 이미 출석체크 한 경우 204
     * - 현재 출석할 수 있는 세션이 존재하지 않는 경우 400
     * - 회원 정보가 정확하지 않은 경우(이름) 401
     * - 거리가 너무 멀어서 출석 요청이 거절된 경우 403
     */
    @PostMapping("/members/me/attend")
    public MemberAttendanceResponse attend(@RequestBody MemberAttendRequest memberAttendRequest) {
        String name = memberAttendRequest.getName();
        Integer memberId = memberService.getMemberByName(name).getId();
        // 출석체크 요청
        AttendanceValue attendanceValue = AttendanceValue.of(
                memberId,
                memberAttendRequest.getLatitude(),
                memberAttendRequest.getLongitude()
        );
        Session session = sessionService.getCurrentSession().orElseThrow(BadRequestException::new);
        Attendance attendance = attendanceService.attend(attendanceValue);
        return MemberAttendanceResponse.of(attendance, session);
    }

    @GetMapping("/members/me/attendances")
    public List<MemberAttendanceResponse> getAttendances(@RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "20") Integer size) {
        return Collections.emptyList();
    }
}
