package com.app.domain.login.service;

import com.app.domain.user.User;
import com.app.domain.user.repository.UserRepository;
import com.app.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceImplTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("로그인 실패 확인")
    void loginCheck() throws Exception {
        // given
        User user = new User("testUser", "guest", "aaa");
        userService.join(user);

        // when
        User loginUser = loginService.login("guest", "bbb");
        assertEquals(loginUser, null);

        // then
    }

}