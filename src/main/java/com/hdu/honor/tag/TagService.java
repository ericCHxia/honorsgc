package com.hdu.honor.tag;

import com.hdu.honor.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final Logger logger = LoggerFactory.getLogger(TagService.class);
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private BaseTagRepository baseTagRepository;

    public Tag get(int id){
        return tagRepository.getTagById(id);
    }

    public List<Tag> getAll(){
        return tagRepository.findAll();
    }

    public Tag save(User user,String detail){
        Tag tag=new Tag(user,detail);
        return save(tag);
    }
    public Tag save(Tag tag){
        BaseTag baseTag = baseTagRepository.saveAndFlush(tag.toBaseTag());
        System.out.println(baseTag);
//        tagRepository.flush();
        return get(baseTag.getId());
    }
}
