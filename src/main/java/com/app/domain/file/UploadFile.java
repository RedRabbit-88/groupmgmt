package com.app.domain.file;

import com.app.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(
        name = "uploadFile_seq_generator",
        sequenceName = "uploadFile_seq",
        initialValue = 1, allocationSize = 1
)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uploadFile_seq_generator")
    @Column(name = "uploadFile_id")
    private Long id;

    private String uploadFileName;
    private String storeFileName;

    @OneToOne(mappedBy = "uploadFile")
    private Member member;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public void changeMember(Member member) {
        this.member = member;
    }
}
