package com.hdu.honor.content;

import com.hdu.honor.user.User;
import com.hdu.honor.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ContentServiceTest {
    @Autowired
    private ContentService contentService;
    @Autowired
    private UserService userService;
    @Test
    void getContentById() {
        System.out.println(contentService.getContentById(13));
    }

    @Test
    void getPage() {
        System.out.println(contentService.getPage(0,2).getContent());
    }

    @Test
    void testGetPage() {
        List<Integer> type = new ArrayList<>();
        type.add(1);
        System.out.println(contentService.getPage(0,2,type).getContent());
    }

    @Test
    void getByUser() {
        User user = userService.getById(8);
        System.out.println(user);
        System.out.println(contentService.getByUser(user));
    }

    @Test
    void count() {
        System.out.println(contentService.count(0));
    }
}