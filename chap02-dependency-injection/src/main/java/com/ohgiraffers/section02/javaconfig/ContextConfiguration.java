package com.ohgiraffers.section02.javaconfig;

import com.ohgiraffers.common.Account;
import com.ohgiraffers.common.MemberDTO;
import com.ohgiraffers.common.PersonalAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfiguration {

    @Bean
    public Account accountGenerator() {
        return  new PersonalAccount(20,"110-233-2222");
    }

    @Bean
    public MemberDTO memberGenerator() {
        MemberDTO member = MemberDTO.builder()
                .seq(30)
                .email("honghong@gmail.com")
                .name("홍석천")
                .personalAccount(accountGenerator())
                .phone("010-3214-5432")
                .build();

        return member;
    }

}
