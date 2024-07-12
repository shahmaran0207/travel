package com.travel.travel.Controller;

import com.travel.travel.DTO.MemberFormDTO;
import com.travel.travel.Entity.Member;
import com.travel.travel.Service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mmc;

    @Autowired
    PasswordEncoder pe;
    @Autowired
    private MockMvc mockMvc;

    public Member createMember(String email, String password) {
        MemberFormDTO form = new MemberFormDTO();
        form.setEmail(email);
        form.setName("홍길동");
        form.setAddress("서울");
        form.setPassword(password);
        Member member = Member.createMember(form, pe);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@naver.com";
        String password = "123456";
        this.createMember(email, password);

        mmc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/Member/Login")
                        .user(email).password(password))
                .andExpect(authenticated());
    }
    
    @Test
    @DisplayName("로그인 실패 테스트")
    public void LoginfailTest() throws Exception{
        String email="test@gmail.com";
        String password="1234";

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/Member/Login")
                .user(email).password("12234"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}
