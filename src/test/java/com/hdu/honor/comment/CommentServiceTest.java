package com.hdu.honor.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Test
    void getByContentId() {
    }

    @Test
    void delete() {
        System.out.println(commentService.delete(3));
    }

    @Test
    void save() {
        System.out.println(commentService.save(2,4,"123456789"));
    }
}