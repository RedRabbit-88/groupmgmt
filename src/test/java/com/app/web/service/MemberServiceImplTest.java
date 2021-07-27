package com.app.web.service;

import com.app.domain.Address;
import com.app.domain.member.Member;
import com.app.domain.member.repository.MemberRepository;
import com.app.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원생성")
    void create() throws Exception {
        // given
        Member member1 = createMember("member1", "test@gmail.com");
        Member member2 = createMember("member2", "test@gmail.com");
        Long member1Id = memberService.join(member1);
        Long member2Id = memberService.join(member2);

        // when
        List<Member> findMembers = memberRepository.findAll();

        // then
        assertEquals(2, findMembers.size());
        assertEquals(member1, memberRepository.findById(member1Id));
        assertEquals(member2, memberRepository.findById(member2Id));
    }

    @Test
    @DisplayName("중복회원 확인")
    void duplicateCheck() throws Exception {
        // given
        Member member1 = createMember("member1", "test@gmail.com");
        memberService.join(member1);

        // when
        Member member2 = createMember("member1", "test@gmail.com");
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

        // then
    }

    @Test
    @DisplayName("회원삭제")
    void delete() throws Exception {
        // given
        Member member1 = createMember("member1", "test@gmail.com");
        memberService.join(member1);

        // when
        memberService.delete(member1);

        // then
        assertEquals("Y", member1.getDeleteFlag());
    }

    private Member createMember(String name, String email) {
        return Member.builder()
                .name(name)
                .email(email)
                .address(new Address("서울", "동작구", "11111"))
                .build();
    }

}