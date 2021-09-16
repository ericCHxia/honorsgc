/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommentService.java
 * @Author: Eric
 * @Date: 2021/8/30 下午2:45
 * @Version: 1.0
 * @LastModified: 2021/8/30 下午2:43
 */

package com.hdu.honor.comment;

import com.hdu.honor.user.User;
import com.hdu.honor.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论信息管理服务
 * @author Eric
 * @version Version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentService(UserService userService, CommentRepository commentRepository) {
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    /**
     * 获取评论
     * @param id 评论的id
     * @return 获取到的评论实例
     */
    public Comment get(int id){
        return commentRepository.getCommentById(id);
    }

    /**
     * 通过ContentId来获取评论
     * @param id ContentId
     * @return 获取到的评论
     * @see Comment
     * @since 1.0
     */
    public List<Comment> getByContentId(int id){
        Iterable<Comment> iterable = commentRepository.getCommentsByContentId(id);
        List<Comment> comments= new ArrayList<>();
        iterable.forEach(comments::add);
        return comments;
    }

    /**
     * 删除评论
     * @param id 评论的Id
     * @return 实际删除的个数
     */
    public Integer delete(int id){
        return commentRepository.deleteCommentById(id);
    }

    /**
     * 更新或者插入新的评论
     * @param comment 所需要更新或者插入的评论
     * @return 插入后的生成评论，比如会生成对应的ID
     */
    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }
    public Comment save(int contentId, User user, String detail){
        Comment comment=new Comment(user,contentId,detail);
        return save(comment);
    }
    public Comment save(int contentId, int userId, String detail){
        return save(contentId,userService.getById(userId),detail);
    }
}
