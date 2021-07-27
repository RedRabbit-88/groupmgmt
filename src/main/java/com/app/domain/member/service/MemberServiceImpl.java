package com.app.domain.member.service;

import com.app.domain.member.Member;
import com.app.domain.member.repository.MemberRepository;
import com.app.web.member.form.MemberForm;
import com.app.domain.Address;
import com.app.web.member.form.MemberSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public List<Member> findByIds(List<Long> memberIds) {
        return memberRepository.findByIds(memberIds);
    }

    /**
     * 여러 조회조건으로 회원 조회
     * 
     * @param memberSearch : 멤버 조회조건
     * @return 조회한 멤버들의 리스트 반환
     */
    @Override
    public List<Member> findByParam(MemberSearch memberSearch) {
        return memberRepository.findByParam(memberSearch);
    }

    /**
     * 회원 생성
     * 
     * @param member : 멤버 엔티티(memberId X)
     * @return 생성된 멤버의 id
     */
    @Override
    @Transactional
    public Long join(Member member) {
        validationDuplicateMemberByName(member);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    @Override
    public void updateMember(Long memberId, MemberForm memberForm) {
        Member findMember = memberRepository.findById(memberId);

        findMember.changeName(memberForm.getName());
        findMember.changeEmail(memberForm.getEmail());

        Address address = Address.builder()
                .city(memberForm.getCity())
                .district(memberForm.getDistrict())
                .zipCode(memberForm.getZipCode())
                .build();
        findMember.changeAddress(address);
    }

    /**
     * 같은 이름으로 존재하는 회원이 있는지 확인
     *
     * @param member : 멤버 엔티티
     */
    private void validationDuplicateMemberByName(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("같은 이름으로 회원이 존재합니다.");
        }
    }

    /**
     * 회원 삭제 (DELT_FLG = "Y" 처리)
     *
     * @param member : 멤버 엔티티
     */
    @Override
    @Transactional
    public void delete(Member member) {
        Member findMember = memberRepository.findById(member.getId());
        findMember.deleteMember();
    }
}
