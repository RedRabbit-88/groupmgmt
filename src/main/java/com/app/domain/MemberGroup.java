package com.app.domain;

import com.app.domain.group.Group;
import com.app.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(
        name = "member_group_seq_generator",
        sequenceName = "member_group_seq",
        initialValue = 1, allocationSize = 1
)
public class MemberGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_group_seq_generator")
    @Column(name = "member_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public MemberGroup(Group group, Member member) {
        this.group = group;
        this.member = member;
    }

    public static MemberGroup createMemberGroup(Member member) {
        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .build();
        return memberGroup;
    }

    public void changeGroup(Group group) {
        this.group = group;
    }
}
