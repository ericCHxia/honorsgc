package com.hdu.honor.comment;

import com.hdu.honor.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 评论类
 * @author Eric
 * @see User
 * @version Version 1.0
 * @since 1.0
 */
@Data
@Entity
@Table(name = "cmt")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "usrid")
    private User usr;
    @Column(name = "contid")
    private int contentId;
    private String detail;
    private Timestamp tim;

    public Comment(User usr, int contentId, String detail) {
        this.usr = usr;
        this.contentId = contentId;
        this.detail = detail;
        this.tim = new Timestamp(new Date().getTime());
    }

}
