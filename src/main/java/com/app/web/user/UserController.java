package com.app.web.user;

import com.app.domain.user.User;
import com.app.domain.user.service.UserService;
import com.app.web.user.form.UserAddForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("form") UserAddForm form) {
        return "users/addUserForm";
    }

    @PostMapping("/add")
    public String save(
            @Valid @ModelAttribute("form") UserAddForm form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "users/addUserForm";
        }

        User user = createUserByForm(form);
        Long userId = userService.join(user);

        if (userId == null) {
            bindingResult.reject("userIdDup", "동일한 유저 ID가 존재합니다.");
            return "users/addUserForm";
        }

        return "redirect:/";
    }

    private User createUserByForm(UserAddForm form) {
        return User.builder()
                .loginId(form.getLoginId())
                .name(form.getName())
                .password(form.getPassword())
                .build();
    }

}
