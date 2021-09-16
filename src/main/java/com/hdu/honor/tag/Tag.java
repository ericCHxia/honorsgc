package com.hdu.honor.tag;

import com.hdu.honor.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tag_count")
public class Tag {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "tagname")
    private String name;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "usrid")
    private User usr;
    private Integer count;
    public Tag(User user,String name) {
        this.name = name;
        this.usr = user;
    }
}
