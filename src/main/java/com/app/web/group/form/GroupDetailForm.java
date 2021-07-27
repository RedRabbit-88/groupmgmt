package com.app.web.group.form;

import com.app.domain.group.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class GroupDetailForm {

    private Integer groupCnt;

    private Integer memberCnt = 0;

    private List<Group> groups = new ArrayList<>();

    private Map<Long, String> membersPerGroup = new ConcurrentHashMap<>();

}
