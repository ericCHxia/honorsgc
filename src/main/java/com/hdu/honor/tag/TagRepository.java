package com.hdu.honor.tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {
    Tag getTagById(int id);
    Tag getTagByName(String name);
}
