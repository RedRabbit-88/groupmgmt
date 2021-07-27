package com.app.domain.group.service;

import com.app.domain.group.Group;
import com.app.domain.member.Member;

import java.util.List;

public interface GroupService {

    /*
     * 조회 기능
     */
    
    /**
     * Round별 그룹 조회
     *
     * @param roundSeq
     * @return List<Group>
     */
    List<Group> findByRoundSeq(Long roundSeq);

    /**
     * 모든 그룹 조회
     * 
     * @return List<Group>
     */
    List<Group> findGroups();

    /**
     * 마지막으로 생성한 그룹 조회
     *
     * @return List<Group>
     */
    List<Group> findLastGroup();


    /*
     * 생성 기능
     */
    
    /**
     * 주어진 회원과 그룹 개수로 그룹을 생성
     *
     * @param members
     * @param groupCnt
     * @return List<Group>
     */
    List<Group> createGroups(List<Member> members, int groupCnt);

    /**
     * 단일 그룹을 저장
     *
     * @param group
     * @return Group의 id
     */
    Long join(Group group);

    /**
     * 여러 그룹을 저장
     *
     * @param groups
     */
    void join(List<Group> groups);
}
