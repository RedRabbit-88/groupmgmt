package com.app.domain.group.repository;

import com.app.domain.group.Group;

import java.util.List;

public interface GroupRepository {

    // 조회 기능
    List<Group> findLastGroup();
    List<Group> findAll();
    Group findById(Long id);
    Long findNextRoundSeq();
    List<Group> findByRoundSeq(Long roundSeq);

    // 생성 기능
    Long save(Group group);

    // 수정 기능
}
