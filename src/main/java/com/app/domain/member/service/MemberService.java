package com.app.domain.member.service;

import com.app.web.member.form.MemberForm;
import com.app.domain.member.Member;
import com.app.web.member.form.MemberSearch;

import java.util.List;

public interface MemberService {
    
    // 조회 기능
    List<Member> findAll();
    Member findById(Long memberId);
    List<Member> findByIds(List<Long> memberIds);
    List<Member> findByParam(MemberSearch memberSearch);

    // 생성 기능
    Long join(Member member);

    // 수정 기능
    void updateMember(Long memberId, MemberForm memberForm);

    // 삭제 기능
    void delete(Member member);

}
