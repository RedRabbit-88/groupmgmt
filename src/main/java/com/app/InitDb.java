package com.app;

import com.app.domain.user.Address;
import com.app.domain.member.Member;
import com.app.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    private final MemberService memberService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            List<Member> memberList = new ArrayList<>();
            memberList.add(getMember("김경미", "temp@gmail.com"));
            memberList.add(getMember("김부년", "temp@gmail.com"));
            memberList.add(getMember("김준영", "temp@gmail.com"));
            memberList.add(getMember("김지영", "temp@gmail.com"));
            memberList.add(getMember("김태웅", "temp@gmail.com"));
            memberList.add(getMember("김형주", "temp@gmail.com"));
            memberList.add(getMember("문경일", "temp@gmail.com"));
            memberList.add(getMember("박세연", "temp@gmail.com"));
            memberList.add(getMember("박재희", "temp@gmail.com"));
            memberList.add(getMember("박찬우", "temp@gmail.com"));
            memberList.add(getMember("박혜리", "temp@gmail.com"));
            memberList.add(getMember("신영재", "temp@gmail.com"));
            memberList.add(getMember("심성윤", "temp@gmail.com"));
            memberList.add(getMember("양동훈", "temp@gmail.com"));
            memberList.add(getMember("이율규", "temp@gmail.com"));
            memberList.add(getMember("이종길", "temp@gmail.com"));
            memberList.add(getMember("전지연", "temp@gmail.com"));
            memberList.add(getMember("현성길", "temp@gmail.com"));
            memberList.add(getMember("황미연", "temp@gmail.com"));

            for (Member member : memberList) {
                em.persist(member);
            }
        }

        private Member getMember(String name, String email) {
            return Member.builder()
                    .name(name)
                    .email(email)
                    .address(new Address("city", "district", "00000"))
                    .build();
        }
    }

}
