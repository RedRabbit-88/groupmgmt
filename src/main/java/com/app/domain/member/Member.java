package com.app.domain.member;

import com.app.domain.file.UploadFile;
import com.app.domain.user.Address;
import com.app.domain.user.HateFood;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(
        name = "member_seq_generator",
        sequenceName = "member_seq",
        initialValue = 1, allocationSize = 1
)
public class Member {

    // 속성
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_nm", nullable = false)
    private String name;

    @Column(name = "member_eml")
    private String email;

    @Embedded
    private Address address;

    @Column(length = 1)
    private String deleteFlag = "N";

    // 연관관계
    @OneToMany(mappedBy = "member")
    private List<HateFood> hateFoodList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "uploadFile_id")
    private UploadFile uploadFile;

    // 생성자
    @Builder
    public Member(Long id, String name, Address address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    // 변경 메서드
    public void changeName(String name) {
        this.name = name;
    }

    public void changeAddress(Address address) {
        this.address = address;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeUploadFile(UploadFile uploadFile) {
        uploadFile.changeMember(this);
        this.uploadFile = uploadFile;
    }

    public void deleteMember() {
        this.deleteFlag = "Y";
    }

}
