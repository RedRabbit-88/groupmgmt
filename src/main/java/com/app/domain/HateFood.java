package com.app.domain;

import com.app.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(
        name = "hateFood_generator",
        sequenceName = "hateFood_seq",
        initialValue = 1, allocationSize = 1
)
public class HateFood {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hateFood_generator")
    @Column(name = "hateFood_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public HateFood(String name) {
        this.name = name;
    }
}
