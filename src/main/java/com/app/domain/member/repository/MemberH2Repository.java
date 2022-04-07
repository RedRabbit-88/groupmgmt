package com.app.domain.member.repository;

import com.app.domain.Address;
import com.app.domain.member.Member;
import com.app.web.member.form.MemberSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.app.domain.member.QMember.*;

@Repository
public class MemberH2Repository implements MemberRepository {

    // JPA Entity Manager
    private final EntityManager em;
    // QueryDSL QueryFactory
    private final JPAQueryFactory query;

    public MemberH2Repository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    /**
     * 전체 회원 조회
     * 
     * @return Member 리스트
     */
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    /**
     * ID로 회원 조회
     *
     * @param id Member.member_id
     * @return Member
     */
    @Override
    public Member findById(Long id) {
        return em.createQuery("select m from Member m where m.id = :member_id", Member.class)
                .setParameter("member_id", id)
                .getSingleResult();
    }

    @Override
    public List<Member> findByIds(List<Long> memberIds) {
        return em.createQuery("select m from Member m where m.id IN :memberIds", Member.class)
                .setParameter("memberIds", memberIds)
                .getResultList();
    }

    /**
     * 회원 동적쿼리 조회
     * 
     * @param memberSearch Member 엔티티 조회객체
     * @return Member 리스트
     */
    @Override
    public List<Member> findByParam(MemberSearch memberSearch) {
        return query.select(member)
                .from(member)
                .where(nameLike(memberSearch.getName()), addressLike(memberSearch.getAddress()))
                .fetch();
    }

    /**
     * 이름으로 회원 조회
     * 
     * @param name Member.name
     * @return Member 리스트
     */
    @Override
    public List<Member> findByName(String name) {
        return query.select(member)
                .from(member)
                .where(nameLike(name), member.deleteFlag.eq("N"))
                .fetch();
    }

    /**
     * 이름 like 검색조건
     */
    private BooleanExpression nameLike(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }

        return member.name.like(name);
    }

    /**
     * 주소 검색조건
     */
    private BooleanExpression addressLike(Address address) {
        if (address == null) {
            return null;
        }
        return member.address.city.eq(address.getCity())
                .or(member.address.district.eq(address.getDistrict()))
                .or(member.address.zipCode.eq(address.getZipCode()));
    }

    /**
     * 회원 저장
     *
     * @param member Member 엔티티
     */
    @Override
    public void save(Member member) {
        em.persist(member);
    }
}
