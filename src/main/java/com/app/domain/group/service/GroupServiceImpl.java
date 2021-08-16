package com.app.domain.group.service;

import com.app.domain.group.Group;
import com.app.domain.group.repository.GroupRepository;
import com.app.domain.member.Member;
import com.app.domain.membergroup.MemberGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public List<Group> findByRoundSeq(Long roundSeq) {
        return groupRepository.findByRoundSeq(roundSeq);
    }

    @Override
    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    @Override
    public List<Group> findLastGroup() {
        return groupRepository.findLastGroup();
    }

    @Transactional
    @Override
    public List<Group> createGroups(List<Member> members, int groupCnt) {

        // 조의 숫자와 회원 숫자 비교 validation
        if (members.size() < groupCnt) {
            throw new IllegalStateException("그룹의 숫자가 회원보다 많습니다.");
        }

        // 회원 무작위로 섞기
        Collections.shuffle(members);

        // 회원들로 회원그룹 생성
        List<MemberGroup> memberGroups = new ArrayList<>();
        for (Member member : members) {
            memberGroups.add(MemberGroup.createMemberGroup(member));
        }

        // 다음 라운드# 구하기
        Long nextRoundSeq = groupRepository.findNextRoundSeq();

        // 그룹 생성
        List<Group> groups = new ArrayList<>();
        for (int groupSeq = 1; groupSeq <= groupCnt; groupSeq++) {
            groups.add(Group.createGroup(Long.valueOf(groupSeq), nextRoundSeq));
        }

        // 그룹에 회원그룹을 할당
        distributeMembersToGroups(groupCnt, memberGroups, groups);

        // 신규 그룹을 입력하기 전에 마지막 그룹을 업데이트
        updateLastGroup();

        // 신규 그룹을 DB에 저장
        for (Group group : groups) {
            groupRepository.save(group);
        }

        return groups;
    }

    private void distributeMembersToGroups(int groupCnt, List<MemberGroup> memberGroups, List<Group> groups) {
        int groupSeq = 0;
        for (MemberGroup memberGroup : memberGroups) {
            groupSeq %= groupCnt;

            Group group = groups.get(groupSeq);
            group.addMemberGroup(memberGroup);

            groupSeq++;
        }
    }

    @Transactional
    @Override
    public Long join(Group group) {
        return groupRepository.save(group);
    }

    @Transactional
    @Override
    public void join(List<Group> groups) {
        Long nextRoundSeq = groupRepository.findNextRoundSeq();

        updateLastGroup();

        for (Group group : groups) {
            group.changeRoundSeq(nextRoundSeq);
            groupRepository.save(group);
        }
    }

    private void updateLastGroup() {
        List<Group> lastGroups = groupRepository.findLastGroup();
        for (Group group : lastGroups) {
            group.changeLastFlag();
        }
    }
}
