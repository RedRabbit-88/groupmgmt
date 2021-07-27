package com.app.domain.user.repository;

import com.app.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserH2Repository implements UserRepository {

    // JPA Entity Manager
    private final EntityManager em;

    @Override
    public User findById(Long userId) {
        return em.createQuery("select u from User u where u.id = :userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public List<User> findByLoginId(String loginId) {
        return em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }
}
