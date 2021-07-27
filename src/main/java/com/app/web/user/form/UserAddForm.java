package com.app.web.user.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserAddForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

}
