package com.app.domain.member.repository;

import com.app.domain.member.Member;
import com.app.web.member.form.MemberSearch;

import java.util.List;

public interface MemberRepository {

    // 조회 기능
    List<Member> findAll();
    Member findById(Long id);
    List<Member> findByIds(List<Long> memberIds);
    List<Member> findByParam(MemberSearch memberSearch);
    List<Member> findByName(String name);

    // 생성 기능
    void save(Member member);
}
