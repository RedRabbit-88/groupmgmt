package com.app.domain.group;

import com.app.domain.membergroup.MemberGroup;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "group_seq_generator",
        sequenceName = "group_seq",
        initialValue = 1, allocationSize = 1
)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq_generator")
    @Column(name = "group_id")
    private Long id;

    private Long groupSeq;
    private Long roundSeq;

    private String lastFlag;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<MemberGroup> memberGroups = new ArrayList<>();

    @Builder
    public Group(Long id, Long groupSeq, Long roundSeq) {
        this.id = id;
        this.groupSeq = groupSeq;
        this.roundSeq = roundSeq;
        this.lastFlag = "Y";
    }

    public static Group createGroup(Long groupSeq, Long nextRoundSeq) {
        Group group = Group.builder()
                .groupSeq(groupSeq)
                .roundSeq(nextRoundSeq)
                .build();

        return group;
    }

    public void addMemberGroup(MemberGroup memberGroup) {
        this.memberGroups.add(memberGroup);
        memberGroup.changeGroup(this);
    }

    public void changeLastFlag() {
        this.lastFlag = "N";
    }

    public void changeRoundSeq(Long roundSeq) {
        this.roundSeq = roundSeq;
    }
}
