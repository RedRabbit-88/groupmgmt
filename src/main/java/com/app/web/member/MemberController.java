package com.app.web.member;

import com.app.domain.file.FileStore;
import com.app.domain.file.UploadFile;
import com.app.domain.member.repository.MemberRepository;
import com.app.domain.user.Address;
import com.app.domain.member.Member;
import com.app.domain.member.service.MemberService;
import com.app.web.member.form.MemberAddForm;
import com.app.web.member.form.MemberUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    @GetMapping
    public String findMembers(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "members/memberList";
    }

    @GetMapping("/new")
    public String addForm(Model model) {
        model.addAttribute("memberForm", new MemberAddForm());
        return "members/memberAddForm";
    }

    @PostMapping("/new")
    public String add(
            @Valid @ModelAttribute("memberForm") MemberAddForm form,
            BindingResult bindingResult,
            @RequestParam MultipartFile profilePic) throws IOException {
        if (bindingResult.hasErrors()) {
            return "members/memberAddForm";
        }

        if (!memberRepository.findByName(form.getName()).isEmpty()) {
            bindingResult.reject("userIdDup", "");
            return "members/memberAddForm";
        }

        Member member = createMemberWithForm(form);
        storeProfilePicAndLinkToMember(profilePic, member);
        memberService.join(member);

        return "redirect:/";
    }

    private void storeProfilePicAndLinkToMember(MultipartFile profilePic, Member member) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(profilePic);
        if (uploadFile != null) {
            member.changeUploadFile(uploadFile);
        }
    }

    public Member createMemberWithForm(MemberAddForm form) throws IOException {
        return Member.builder()
                .name(form.getName())
                .email(form.getEmail())
                .address(new Address(form.getCity(), form.getDistrict(), form.getZipCode()))
                .build();
    }

    @GetMapping("/{memberId}/update")
    public String updateForm(
            Model model,
            @PathVariable(name = "memberId") Long memberId
    ) {
        Member findMember = memberService.findById(memberId);
        MemberUpdateForm memberForm = setMemberUpdateFormWithMember(findMember);

        model.addAttribute("memberForm", memberForm);
        return "members/memberUpdateForm";
    }

    private MemberUpdateForm setMemberUpdateFormWithMember(Member findMember) {
        MemberUpdateForm memberForm = new MemberUpdateForm();

        memberForm.setName(findMember.getName());
        memberForm.setEmail(findMember.getEmail());
        memberForm.setCity(findMember.getAddress().getCity());
        memberForm.setDistrict(findMember.getAddress().getDistrict());
        memberForm.setZipCode(findMember.getAddress().getZipCode());
        memberForm.setUploadFile(findMember.getUploadFile());

        return memberForm;
    }

    @PostMapping("/{memberId}/update")
    public String update(
            @PathVariable(name = "memberId") Long memberId,
            @ModelAttribute(name = "memberForm") MemberUpdateForm form,
            BindingResult bindingResult,
            @RequestParam MultipartFile profilePic
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "members/memberUpdateForm";
        }

        Member findMember = memberRepository.findById(memberId);

        if (!profilePic.isEmpty() && findMember.getUploadFile() != null) {
            fileStore.deleteFile(findMember.getUploadFile().getStoreFileName());
        }
        storeProfilePicAndLinkToMember(profilePic, findMember);
        memberService.updateMember(findMember, form);

        return "redirect:/members";
    }

    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }
}
