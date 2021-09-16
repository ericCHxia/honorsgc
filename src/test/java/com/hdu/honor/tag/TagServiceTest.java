package com.hdu.honor.tag;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagServiceTest {
    @Autowired
    private TagService tagService;
    @Test
    void get() {
        System.out.println(tagService.get(1));
    }

    @Test
    void getAll() {
        System.out.println(tagService.getAll());
    }
}