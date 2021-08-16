package com.app.web.member.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberAddForm {

    @NotBlank
    private String name;
    
    // email 포맷 검사 필요
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String city;

    @NotBlank
    private String district;

    @NumberFormat
    @NotBlank
    private String zipCode;

    private MultipartFile profilePic;

}
