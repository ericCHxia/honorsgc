package com.hdu.honor.content;

import com.hdu.honor.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content,Integer> {
    Content getContentById(int id);
    Integer deleteContentById(int id);
    Page<Content> findAllByTypIn(Pageable pageable, List<Integer> type);
    List<Content> getContentsByUsr(User user);
    Integer countContentByTyp(int typ);
}
