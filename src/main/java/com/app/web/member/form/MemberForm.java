package com.app.web.member.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;
    
    // email 포맷 검사 필요
    @Email
    private String email;

    private String city;
    private String district;

    @NumberFormat
    private String zipCode;

}
