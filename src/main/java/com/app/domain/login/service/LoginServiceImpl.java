package com.app.domain.login.service;

import com.app.domain.user.User;
import com.app.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Override
    public User login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .stream().filter(u -> u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
