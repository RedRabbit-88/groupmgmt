package com.app.domain.login.service;

import com.app.domain.user.User;

public interface LoginService {

    User login(String loginId, String password);

}
