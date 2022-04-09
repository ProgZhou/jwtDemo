package com.security.demo;

import com.security.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest
class Demo02ApplicationTests {

    @Autowired
    UserDetailsService userDetailsService;

    @Test
    void contextLoads() {
    }

    @Test
    void testService(){
        UserDetails root = userDetailsService.loadUserByUsername("mary");
        System.out.println(root);
    }

}
