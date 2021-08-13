package com.app.domain.user.service;

import com.app.domain.user.User;
import com.app.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("단일 유저 생성 확인")
    void createUser() throws Exception {
        // given
        User user = new User("test", "guest", "aaa");
        userService.join(user);

        // when
        List<User> loginUsers = userRepository.findByLoginId("test");
        assertEquals(loginUsers.size(), 1);

        // then
    }

    @Test
    @DisplayName("동명이인 유저 생성 확인")
    void createMultiUsers() throws Exception {
        // given
        User user1 = new User("test", "guest", "aaa");
        User user2 = new User("test", "guest", "aaa");

        Long user1Id = userService.join(user1);
        Long user2Id = userService.join(user2);

        // when
        assertEquals(user1.getId(), user1Id);
        assertEquals(user2.getId(), null); // 동일ID로 유저 생성 불가
        
        // then
    }

}