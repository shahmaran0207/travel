package com.travel.travel.Controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("상품 등록 페이지 권한 테스트")
    @WithMockUser(username = "관리자", roles="ADMIN")
    public void itemFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Admin/Item/new"))
                .andDo(print())
                .andExpect(status().isOk());
    }

   @Test
   @DisplayName("상품 등록 페이지 일반 회원 접근 테스트")
    @WithMockUser(username="테스터", roles = "USER")
    public void itemFormNotAdminTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/Admin/Item/new"))
                .andDo(print())
                .andExpect(status().isForbidden());
   }
}