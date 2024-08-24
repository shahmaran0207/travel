package com.travel.travel.Controller;

import com.travel.travel.DTO.MemberFormDTO;
import com.travel.travel.Entity.Member;
import com.travel.travel.Service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Member")
public class MemberController {

    private final MemberService ms;
    private final PasswordEncoder pe;

    @GetMapping(value = "/new")
    public String newMember(Model model) {
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "Member/MemberForm";
    }

    @PostMapping(value = "/new")
    public String MemberForm(@Valid MemberFormDTO mfd, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "Member/MemberForm";
        }

        try {
            Member member = Member.createMember(mfd, pe);
            ms.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "Member/MemberForm";
        }

        return "redirect:/thymeleafEX/thymeleafEX07";
    }

    @GetMapping(value="/Login")
    public String login(){
        return "/Member/Login";
    }

    @GetMapping("/Login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 혹은 비밀번호를 확인해주세요");
        return "Member/Login";
    }

}
