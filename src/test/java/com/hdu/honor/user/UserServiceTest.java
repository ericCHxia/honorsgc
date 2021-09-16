package com.hdu.honor.user;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    void getById() {
        System.out.println(userService.getById(4));
    }

    @Test
    void loadUserByUsername() {
        User user = userService.loadUserByUsername("admin");
        System.out.println(user);
        System.out.println(user.getAuthorities());
    }

    @Test
    void getAllAttends() {
        System.out.println(userService.getAllAttends(0,10,null).getContent());
    }
}