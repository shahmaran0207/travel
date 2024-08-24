package com.travel.travel.Entity;

import com.travel.travel.Repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "gildong", roles = "USER") // gildong이라는 USER가 로그인 한 상태라고 가정하고 테스트 진행
    public void auditingTest(){
        Member newmember=new Member();
        memberRepository.save(newmember);

        em.flush();
        em.clear();

        Member member=memberRepository.findById(newmember.getId()).
                orElseThrow(EntityNotFoundException::new);

        System.out.println("register time: "+member.getRegTime());
        System.out.println("update time: "+member.getUpdateTime());
        System.out.println("create time: "+member.getCreatedBy());
        System.out.println("modify member: "+member.getModifiedBy());
    }
}
