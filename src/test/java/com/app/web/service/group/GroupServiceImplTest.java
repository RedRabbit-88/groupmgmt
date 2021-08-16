package com.app.web.service.group;

import com.app.domain.user.Address;
import com.app.domain.group.Group;
import com.app.domain.member.Member;
import com.app.domain.group.service.GroupService;
import com.app.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupServiceImplTest {

    @Autowired private MemberService memberService;
    @Autowired private GroupService groupService;

    private Member getMember(String name, String email) {
        return Member.builder()
                .name(name)
                .email(email)
                .address(new Address("city", "district", "zipCode"))
                .build();
    }

    @Test
    @DisplayName("조의 숫자가 회원보다 많을 경우 에러")
    void groupingNumberCheck() throws Exception {
        // given
        List<Member> memberList = new ArrayList<>();
        memberList.add(getMember(String.valueOf(1), String.valueOf(1) + "@gmail.com"));

        for (Member member : memberList) {
            memberService.join(member);
        }

        // when
        assertThrows(IllegalStateException.class, () -> {
            List<Group> groups = groupService.createGroups(memberList, 2);
        });

        // then
    }

    @Test
    @DisplayName("회원 6명을 그룹 5개로 나누면 첫번째 그룹은 회원이 2명 있어야 한다.")
    void grouping() throws Exception {
        // given
        List<Member> memberList = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            memberList.add(getMember(String.valueOf(i), String.valueOf(i) + "@gmail.com"));
        }

        for (Member member : memberList) {
            memberService.join(member);
        }
        assertEquals(memberList.size(), 6);

        // when
        List<Group> groups = groupService.createGroups(memberList, 5);

        // then
        assertEquals(groups.get(0).getMemberGroups().size(), 2);
    }

}