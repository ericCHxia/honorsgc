package com.hdu.honor.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Comment getCommentById(int id);
    Iterable<Comment> getCommentsByContentId(int contentId);
    Integer deleteCommentById(int id);
}
