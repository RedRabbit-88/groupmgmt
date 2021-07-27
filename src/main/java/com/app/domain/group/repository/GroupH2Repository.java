package com.app.domain.group.repository;

import com.app.domain.group.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupH2Repository implements GroupRepository {

    private final EntityManager em;

    @Override
    public List<Group> findLastGroup() {
        return em.createQuery(
                "select g from Group g" +
                        " where g.lastFlag = 'Y'", Group.class)
                .getResultList();
    }

    @Override
    public List<Group> findAll() {
        return em.createQuery(
                "select g from Group g" +
                        " order by g.roundSeq desc", Group.class)
                .getResultList();
    }

    @Override
    public Group findById(Long id) {
        return em.createQuery(
                "select g from Group g" +
                        " where g.id = :id", Group.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Long findNextRoundSeq() {
        Integer nextRoundSeq = em.createQuery(
                "select NVL(MAX(g.roundSeq), 0) + 1 from Group g", Integer.class)
                .getSingleResult();
        return Long.valueOf(nextRoundSeq);
    }

    @Override
    public Long save(Group group) {
//        List<MemberGroup> memberGroups = group.getMemberGroups();
//        for (MemberGroup memberGroup : memberGroups) {
//            em.persist(memberGroup);
//        }

        em.persist(group);

        return group.getId();
    }

    @Override
    public List<Group> findByRoundSeq(Long roundSeq) {
        return em.createQuery(
                "select distinct g from Group g" +
                        " join fetch g.memberGroups mg" +
                        " join fetch mg.member m" +
                        " where g.roundSeq = :roundSeq", Group.class)
                .setParameter("roundSeq", roundSeq)
                .getResultList();
    }
}
