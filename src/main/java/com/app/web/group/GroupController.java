package com.app.web.group;

import com.app.domain.member.Member;
import com.app.web.group.form.GroupCreateForm;
import com.app.web.group.form.GroupDetailForm;
import com.app.web.group.form.GroupListForm;
import com.app.domain.group.Group;
import com.app.domain.group.service.GroupService;
import com.app.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"groupCreateForm"})
@RequestMapping("/groups")
public class GroupController {

    private final MemberService memberService;
    private final GroupService groupService;

    @ModelAttribute("members")
    public List<Member> members() {
        return memberService.findAll();
    }

    /**
     * 신규 그룹 생성 화면로 이동
     * 
     * @param model
     * @return 신규 그룹 생성 UI
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("groupCreateForm", new GroupCreateForm());
        return "groups/groupCreateForm";
    }


    /**
     * 그룹을 생성하는 메서드
     *
     * @param form
     * @param bindingResult
     * @return 정상 - /groups/create 그룹이 임시 생성된 화면으로 이동
     * 비정상 - /groups/createGroupForm 그룹 생성화면으로 다시 이동
     */
    @PostMapping("/new")
    public String newGroup(
            @ModelAttribute(name = "groupCreateForm") @Valid GroupCreateForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (form.getMembers() != null && form.getGroupCnt() != null) {
            if (form.getMembers().size() < form.getGroupCnt()) {
                bindingResult.reject("groupMemberCntError", "");
            }
        }

        if (bindingResult.hasErrors()) {
            return "groups/groupCreateForm";
        }

        List<Group> groups = groupService.createGroups(memberService.findByIds(form.getMembers()), form.getGroupCnt());
        redirectAttributes.addAttribute("roundSeq", groups.get(0).getRoundSeq());

        return "redirect:/groups/{roundSeq}";
    }

    
    /**
     * 그룹으로 나눠진 유저들을 round별로 조회하는 메서드
     *  
     * @param roundSeq
     * @param model
     * @return groupForm.html
     */
    @GetMapping("/{roundSeq}")
    public String groupListOfRound(@PathVariable("roundSeq") Long roundSeq, Model model) {
        List<Group> findGroups = groupService.findByRoundSeq(roundSeq);

        GroupDetailForm groupDetailForm = createGroupDetailForm(findGroups);
        model.addAttribute("groupDetailForm", groupDetailForm);

        return "groups/groupDetailForm";
    }

    /**
     * 모든 그룹을 조회하는 메서드
     * 
     * @param model
     * @return
     */
    @GetMapping
    public String groupList(Model model) {
        List<Group> groups = groupService.findGroups();
        Map<Long, List<Group>> groupsPerRound =
                groups.stream().collect(Collectors.groupingBy(Group::getRoundSeq));

        Map<Long, Long> membersPerRound = getMembersPerRound(groupsPerRound);

        GroupListForm form = new GroupListForm();
        form.setGroupsPerRound(groupsPerRound);
        form.setMembersPerRound(membersPerRound);

        model.addAttribute("form", form);
        return "groups/groupList";
    }

    private GroupDetailForm createGroupDetailForm(List<Group> findGroups) {
        int memberCnt = 0;
        Map<Long, String> membersPerGroup = new ConcurrentHashMap<>();
        for (Group group : findGroups) {
            memberCnt += group.getMemberGroups().size();

            // 해당 Group의 Member 이름을 A / B 형식으로 생성
            String memberNames = group.getMemberGroups().stream()
                    .map(mb -> mb.getMember().getName())
                    .sorted()
                    .collect(Collectors.joining(" / "));

            // Group.id - MemberNames 로 맵에 입력
            membersPerGroup.put(group.getGroupSeq(), memberNames);
        }

        GroupDetailForm form = new GroupDetailForm();
        form.setGroups(findGroups);
        form.setMemberCnt(memberCnt);
        form.setMembersPerGroup(membersPerGroup);
        return form;
    }

    private Map<Long, Long> getMembersPerRound(Map<Long, List<Group>> groupsPerRound) {
        Map<Long, Long> membersPerRound = new ConcurrentHashMap<>();
        for (Long roundSeq : groupsPerRound.keySet()) {
            Long memberCnt = 0L;
            for (Group g : groupsPerRound.get(roundSeq)) {
                memberCnt += g.getMemberGroups().size();
            }
            membersPerRound.put(roundSeq, memberCnt);
        }
        return membersPerRound;
    }
}
