package com.app.domain.user.service;

import com.app.domain.user.User;

public interface UserService {

    User findById(Long userId);

    Long join(User user);

}
