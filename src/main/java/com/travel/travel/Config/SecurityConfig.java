package com.travel.travel.Config;

import com.travel.travel.Service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MemberService memberService;

    public SecurityConfig(MemberService memberService) {
        this.memberService = memberService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf // CSRF 보호 활성화
                        .ignoringRequestMatchers("/h2-console/**") // 필요한 경우 특정 경로 무시
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Member/Login", "/Member/new", "/h2-console/**").permitAll() // 접근 허용 경로 설정
                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/Member/Login") // 사용자 정의 로그인 페이지
                        .defaultSuccessUrl("/") // 로그인 성공 시 리다이렉트 경로
                        .usernameParameter("email") // 사용자 이름 파라미터
                        .failureUrl("/Member/Login/error") // 로그인 실패 시 리다이렉트 경로
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/Member/Logout")) // 로그아웃 경로
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 경로
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return memberService;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(memberService).passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }
}
