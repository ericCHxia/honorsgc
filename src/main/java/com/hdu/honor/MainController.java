/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: MainController.java
 * @Author: Eric
 * @Date: 2021/8/31 上午11:18
 * @Version: 1.0
 * @LastModified: 2021/8/31 上午11:15
 */

package com.hdu.honor;

import com.hdu.honor.comment.Comment;
import com.hdu.honor.comment.CommentService;
import com.hdu.honor.content.Content;
import com.hdu.honor.content.ContentService;
import com.hdu.honor.exception.HttpInvalidParameterException;
import com.hdu.honor.exception.PageNotFindException;
import com.hdu.honor.image.ImageUploadException;
import com.hdu.honor.image.ImageUploadResponse;
import com.hdu.honor.image.ImgService;
import com.hdu.honor.tag.Tag;
import com.hdu.honor.tag.TagService;
import com.hdu.honor.user.User;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private  ContentService contentService;
    @Autowired
    private  CommentService commentService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ImgService imgService;

    private final Logger logger = LoggerFactory.getLogger(MainController.class);

    //Markdown解析器
    private final HtmlRenderer renderer ;
    private final Parser parser;

    public MainController() {
        MutableDataSet options= new MutableDataSet();
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    @GetMapping({"/", "/index.html","/index"})
    public String index(Model model,
                        @RequestParam(value = "page",required = false,defaultValue = "0") Integer pageNumber,
                        @RequestParam(value = "type",required = false,defaultValue = "-1") Integer type){
        List<Integer> types = new ArrayList<>();
        types.add(type);
        Page<Content> page=contentService.getPage(pageNumber,3,types);
        int totalPage=page.getTotalPages();
        if (totalPage<=pageNumber){
            throw new PageNotFindException();
        }
        String title;
        int op;
        switch (type){
            case 0:title="资源分享";op=2;break;
            case 1:title="通知公告";op=1;break;
            default:title="全部";op=0;
        }
        model.addAttribute("title",title+" - 卓越学院学习分享平台");
        model.addAttribute("contents",page.getContent());
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("op",op);
        return "index";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable Integer id,
                       Model model,
                       Authentication authentication){
        Content content=contentService.getContentById(id);
        User user = (User) authentication.getPrincipal();
        logger.info(user.toString());
        logger.info(user.getAuthorities().toString());
        if (content==null){
            throw new PageNotFindException();
        }
        Node document = parser.parse(content.getDetail());
        model.addAttribute("detail",renderer.render(document));
        model.addAttribute("content",content);
        model.addAttribute("comments",commentService.getByContentId(content.getId()));
        return "post";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,Model model){
        throw new PageNotFindException();
    }

    @GetMapping("/del/{id}")
    public String deleteContent(@PathVariable Integer id,Model model){
        throw new PageNotFindException();
    }

    @GetMapping("/change/{id}")
    public String changeContentState(@PathVariable Integer id, Mode model){
        throw new PageNotFindException();
    }

    @GetMapping("/delcmt")
    public String deleteComment(@RequestParam(required = false,defaultValue = "0") Integer id,
                                Authentication authentication,
                                @RequestHeader(value = "Referer",defaultValue = "") String referer,
                                HttpServletRequest request){
        User user = (User) authentication.getPrincipal();
        Comment comment = commentService.get(id);
        if (request.isUserInRole("ADMIN")||comment.getUsr().getNum().equals(user.getNum())){
            commentService.delete(id);
        }else {
            throw new AccessDeniedException("无法删除");
        }
        if (referer.equals("")){
            return "redirect:/";
        }
        return "redirect:"+referer;
    }

    @PostMapping("/commit_comment")
    public String commitComment(Authentication authentication,
                                HttpServletRequest request,
                                @RequestHeader(value = "Referer",defaultValue = "") String referer){
        User user = (User) authentication.getPrincipal();
        String contentIdStr = request.getParameter("contentId");
        int contentId;
        try {
            contentId=Integer.parseInt(contentIdStr);
        }catch (Exception e){
            throw new HttpInvalidParameterException();
        }
        Content content=contentService.getContentById(contentId);
        if (content==null||content.getTyp()!=0){
            throw new HttpInvalidParameterException();
        }

        String detail = request.getParameter("detail");
        Comment comment = new Comment(user,contentId,detail);
        comment = commentService.save(comment);
        if (comment!=null){
            if (referer.equals(""))return "redirect:/";
            else return "redirect:"+referer;
        }
        throw new HttpInvalidParameterException();
    }
    @PostMapping("/commit")
    public String commit_content(Authentication authentication,
                                 HttpServletRequest request,
                                 @RequestHeader(value = "Referer",defaultValue = "") String referer){
        User user = (User) authentication.getPrincipal();
        String title = request.getParameter("title");
        String describe = request.getParameter("dsc");
        int type= Integer.parseInt(request.getParameter("typ"));
        int tagId = Integer.parseInt(request.getParameter("tag"));
        String detail = request.getParameter("detail");
        Content content = contentService.save(user,title,describe,detail,type,tagId);
        if (content!=null){
            if (referer.equals(""))return "redirect:/";
            else return "redirect:"+referer;
        }
        throw new HttpInvalidParameterException();
    }

    @PostMapping("/commit_tag")
    public String commitTag(Authentication authentication,
                            HttpServletRequest request,
                            @RequestHeader(value = "Referer",defaultValue = "") String referer){
        User user = (User) authentication.getPrincipal();
        String tagName = request.getParameter("tagname");
        Tag tag = tagService.save(user,tagName);
        if (tag!=null){
            if (referer.equals(""))return "redirect:/";
            else return "redirect:"+referer;
        }
        throw new HttpInvalidParameterException();
    }

    @GetMapping("/add")
    public String add_content(Model model){
        List<Tag> tags=tagService.getAll();
        model.addAttribute("tags",tags);
        return "addmd";
    }

    @GetMapping("/add_tag")
    public String add_tag(){
        return "add_tag";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ImageUploadResponse upload_img(Authentication authentication,
                                          @RequestParam(name = "editormd-image-file")MultipartFile file){
        ImageUploadResponse uploadResponse= new ImageUploadResponse();
        try {
            String filename = imgService.save(file);
            uploadResponse.setSuccess(1);
            uploadResponse.setMessage("上传成功");
            uploadResponse.setUrl(imgService.getUrl(filename));
        }catch (ImageUploadException exception){
            uploadResponse.setSuccess(0);
            uploadResponse.setMessage(exception.getMessage());
        }
        return uploadResponse;
    }
}
