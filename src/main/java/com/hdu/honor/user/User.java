package com.hdu.honor.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "clas")
    private String classId;
    private String num;
    @Column(name = "subj")
    private String subject;
    @Column(name = "sch")
    private String college;
    private String qq;
    private String pwd;
    @Column(name = "priv")
    private int privilege;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        for (int i = 0; i <=privilege; i++) {
            auths.add(privilege2Authority(i));
        }
        return auths;
    }
    private GrantedAuthority privilege2Authority(int id){
        final String[] authorityName={"ROLE_USER","ROLE_ADMIN","ROLE_SUPER"};
        return new SimpleGrantedAuthority(authorityName[id]);
    }
    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
