package com.app.domain.user.service;

import com.app.domain.user.User;
import com.app.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Transactional
    public Long join(User user) {
        if (validationDuplicateUser(user)) {
            userRepository.save(user);
        }
        return user.getId();
    }

    private boolean validationDuplicateUser(User user) {
        return userRepository.findByLoginId(user.getLoginId()).isEmpty() ? true : false;
    }

}
