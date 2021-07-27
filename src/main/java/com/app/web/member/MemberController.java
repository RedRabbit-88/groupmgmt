package com.app.web.member;

import com.app.web.member.form.MemberForm;
import com.app.domain.Address;
import com.app.domain.member.Member;
import com.app.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String findMembers(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "members/memberList";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/addMemberForm";
    }

    @PostMapping("/new")
    public String create(
            @Valid @ModelAttribute("form") MemberForm form,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        Member member = createMemberByForm(form);
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/{memberId}/update")
    public String updateMemberForm(
            Model model,
            @PathVariable(name = "memberId") Long memberId
    ) {
        Member findMember = memberService.findById(memberId);
        MemberForm memberForm = setMemberFormWithMember(findMember);

        model.addAttribute("memberForm", memberForm);
        return "members/updateMemberForm";
    }

    @PostMapping("/{memberId}/update")
    public String updateMember(
            @PathVariable(name = "memberId") Long memberId,
            @ModelAttribute(name = "memberForm") MemberForm memberForm
    ) {
        memberService.updateMember(memberId, memberForm);
        return "redirect:/members";
    }

    private Member createMemberByForm(MemberForm form) {
        return Member.builder()
                .name(form.getName())
                .email(form.getEmail())
                .address(new Address(form.getCity(), form.getDistrict(), form.getZipCode()))
                .build();
    }

    private MemberForm setMemberFormWithMember(Member findMember) {
        MemberForm memberForm = new MemberForm();

        memberForm.setName(findMember.getName());
        memberForm.setEmail(findMember.getEmail());
        memberForm.setCity(findMember.getAddress().getCity());
        memberForm.setDistrict(findMember.getAddress().getDistrict());
        memberForm.setZipCode(findMember.getAddress().getZipCode());

        return memberForm;
    }
}
