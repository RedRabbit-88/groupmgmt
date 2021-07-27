package com.app.domain.user.repository;

import com.app.domain.user.User;

import java.util.List;

public interface UserRepository {

    User findById(Long userId);

    List<User> findByLoginId(String loginId);

    void save(User user);

}
