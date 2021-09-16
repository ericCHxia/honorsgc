package com.hdu.honor.content;

import com.hdu.honor.comment.Comment;
import com.hdu.honor.tag.Tag;
import com.hdu.honor.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="contents")
@NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "usrid")
    private User usr;
    private int typ;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "tag")
    private Tag tag;
    private Timestamp tim;
    private String title;
    private String dsc;
    private String detail="";
    private int stat;
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "contid")
    private List<Comment> comments;
    public Content(User usr, int typ, Tag tag, String title, String dsc, String detail) {
        this.usr = usr;
        this.typ = typ;
        this.tag = tag;
        this.title = title;
        this.dsc = dsc;
        this.detail = detail;
        this.tim = new Timestamp(new Date().getTime());
    }
}
