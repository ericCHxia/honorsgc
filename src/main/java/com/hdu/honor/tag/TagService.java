package com.hdu.honor.tag;

import com.hdu.honor.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag get(int id){
        return tagRepository.getTagById(id);
    }

    public List<Tag> getAll(){
        return tagRepository.findAll();
    }

    public Tag save(User user,String detail){
        Tag tag=new Tag(user,detail);
        return tagRepository.save(tag);
    }
    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }
}
