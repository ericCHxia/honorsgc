package com.hdu.honor.content;

import com.hdu.honor.community.Community;
import com.hdu.honor.tag.Tag;
import com.hdu.honor.tag.TagService;
import com.hdu.honor.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private TagService tagService;

    /**
     * 获取文章
     * @param id 文章的Id
     * @return 文章
     */
    public Content getContentById(int id) {
        return contentRepository.getContentById(id);
    }

    /**
     * 根据页面获取Content
     * @param pageNumber 页面的号码从0开始
     * @param pageSize 页面的大小
     * @return 页面
     */
    public Page<Content> getPage(int pageNumber,int pageSize) {
        //TODO: 现在只实现了按照时间逆序，还需要实现置顶
        Sort sort = Sort.by(Sort.Direction.DESC,"tim");
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return contentRepository.findAll(pageable);
    }

    public Page<Content> getPage(int pageNumber, int pageSize, List<Integer> types){
        if (types.contains(-1)){
            return getPage(pageNumber,pageSize);
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"tim");
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return contentRepository.findAllByTypIn(pageable,types);
    }

    public Page<Content> getPage(int pageNumber,int pageSize,Specification<Content> specification) {
        //TODO: 现在只实现了按照时间逆序，还需要实现置顶
        Sort sort = Sort.by(Sort.Direction.DESC,"tim");
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return contentRepository.findAll(specification,pageable);
    }

    /**
     * 删除文章
     * @param id 文章的Id
     * @return 删除的文章数量
     */
    public Integer delete(int id){
        return contentRepository.deleteContentById(id);
    }

    public Content save(Content content){
        return contentRepository.save(content);
    }

    public Content save(User user,String title,String describe,String detail,int type,int tagId){
        Tag tag = tagService.get(tagId);
        Content content = new Content(user,type,tag,title,describe,detail);
        return save(content);
    }

    public List<Content> getByUser(User user){
        return contentRepository.getContentsByUsr(user);
    }

    public Integer count(int typ){
        return contentRepository.countContentByTyp(typ);
    }
}
