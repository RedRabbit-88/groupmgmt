package com.app.web.group.form;

import com.app.domain.group.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class GroupListForm {

    private Map<Long, List<Group>> groupsPerRound = new ConcurrentHashMap<>();

    private Map<Long, Long> membersPerRound = new ConcurrentHashMap<>();

}
