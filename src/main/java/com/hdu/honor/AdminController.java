/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: AdminController.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 上午12:42
 */

package com.hdu.honor;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.hdu.honor.community.Community;
import com.hdu.honor.community.CommunityService;
import com.hdu.honor.community.type.CommunityType;
import com.hdu.honor.community.type.CommunityTypeService;
import com.hdu.honor.config.PageSizeConfig;
import com.hdu.honor.content.Content;
import com.hdu.honor.content.ContentService;
import com.hdu.honor.exception.HttpInvalidParameterException;
import com.hdu.honor.exception.PageNotFindException;
import com.hdu.honor.tag.TagService;
import com.hdu.honor.user.User;
import com.hdu.honor.user.UserAttend;
import com.hdu.honor.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final SimpleDateFormat exportDateFormat = new SimpleDateFormat("yyyyMMdd");
    @Autowired
    private ContentService contentService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommunityTypeService typeService;
    @Autowired
    private PageSizeConfig pageSizeConfig;
    @GetMapping({"/",""})
    public String index(Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user",user);
        model.addAttribute("article_num",contentService.count(0));
        model.addAttribute("notice_num",contentService.count(1));
        model.addAttribute("gtt_num",communityService.count());
        model.addAttribute("user_num",userService.count());
        model.addAttribute("admin_num",userService.countAdmin());
        model.addAttribute("java_version",System.getProperty("java.version"));
        model.addAttribute("sys_name",System.getProperty("os.name"));
        model.addAttribute("sys_arch",System.getProperty("os.arch"));
        return "admin/index";
    }
    @PostMapping("/user_save")
    public String userSave(Authentication authentication,
                           @RequestHeader(value = "Referer",defaultValue = "/admin/") String referer,
                           @RequestParam(value = "id",required = false,defaultValue = "-1") Integer userId,
                           HttpServletRequest request,
                           Model model){
        User user = (User) authentication.getPrincipal();
        User targetUser;
        if (userId==-1){
            targetUser=user;
        }else{
            targetUser=userService.getById(userId);
        }
        if (targetUser==null){
            model.addAttribute("url",referer);
            model.addAttribute("message","用户不存在");
            return "redirection";
        }
        if (request.getParameter("num")!=null){
            targetUser.setNum(request.getParameter("num"));
        }
        if (request.getParameter("subj")!=null){
            targetUser.setSubject(request.getParameter("subj"));
        }
        if (request.getParameter("sch")!=null){
            targetUser.setCollege(request.getParameter("sch"));
        }
        if (request.getParameter("qq")!=null){
            targetUser.setQq(request.getParameter("qq"));
        }
        if (request.getParameter("clas")!=null){
            targetUser.setClassId(request.getParameter("clas"));
        }
        if (request.getParameter("name")!=null){
            targetUser.setName(request.getParameter("name"));
        }
        if (request.getParameter("priv")!=null){
            int priv=Integer.parseInt(request.getParameter("priv"));
            if (priv!=targetUser.getPrivilege()&&user.getPrivilege()!=2){
                model.addAttribute("url",referer);
                model.addAttribute("message","没有权限修改");
                return "redirection";
            }
            if (priv<0||priv>2){
                throw new HttpInvalidParameterException();
            }
            targetUser.setPrivilege(priv);
        }
        targetUser = userService.save(targetUser);
        if (userId==-1){
            UserService.flushUser(targetUser);
        }
        model.addAttribute("url",referer);
        model.addAttribute("message","保存成功");
        return "redirection";
    }
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("共同体统计", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<Community> communities = communityService.getAll();
        EasyExcel.write(response.getOutputStream(), Community.class).sheet("模板").doWrite(communities);
    }
    @GetMapping("/article")
    public String article(Authentication authentication,
                          Model model,
                          @RequestParam(value = "page",required = false,defaultValue = "0") Integer pageNumber){
        User user = (User) authentication.getPrincipal();
        List<Integer> types = new ArrayList<>();
        types.add(0);
        Page<Content> page=contentService.getPage(pageNumber,pageSizeConfig.getAdmin(),types);
        model.addAttribute("title","文章管理 - 卓越平台管理中心");
        model.addAttribute("op",1);
        model.addAttribute("user",user);
        model.addAttribute("page",page);
        return "admin/article";
    }
    @GetMapping("/notice")
    public String notice(Authentication authentication,
                         Model model,
                         @RequestParam(value = "page",required = false,defaultValue = "0") Integer pageNumber){
        User user = (User) authentication.getPrincipal();
        List<Integer> types = new ArrayList<>();
        types.add(1);
        Page<Content> page=contentService.getPage(pageNumber,pageSizeConfig.getAdmin(),types);
        model.addAttribute("title","公告管理 - 卓越平台管理中心");
        model.addAttribute("op",2);
        model.addAttribute("user",user);
        model.addAttribute("page",page);
        return "admin/article";
    }
    @GetMapping("/community")
    public String communityAdmin(Authentication authentication,
                                 Model model,
                                 @RequestParam(value = "page",required = false,defaultValue = "0") Integer pageNumber,
                                 @RequestParam(value = "search", required = false, defaultValue = "") String searchText,
                                 @RequestParam(value = "startDate",required = false,defaultValue = "2001-02-25") @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd")Date startDate,
                                 @RequestParam(value = "endDate",required = false,defaultValue = "2001-02-25") @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd")Date endDate){
        if (endDate.getYear()==2001-1900){
            endDate=new Date();
        }
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user",user);
        model.addAttribute("op",3);
        Date finalEndDate = endDate;
        Specification<Community> spec = (root, query, cb) ->{
            List<Predicate> list = new ArrayList<>();
            if (!searchText.equals("")){
                list.add(cb.like(root.get("title"),"%"+ searchText +"%"));
            }
            list.add(cb.between(root.get("tim"),startDate, finalEndDate));
            Predicate[] predicates = new Predicate[list.size()];
            return cb.and(list.toArray(predicates));
        };
        Page<Community> page=communityService.getAll(pageNumber,pageSizeConfig.getAdmin(),spec);
        model.addAttribute("page",page);
        model.addAttribute("communities",page.getContent());
        return "admin/community";
    }
    @PostMapping("/community/del")
    public String delCommunity(Model model,
                               @RequestParam(name = "checkbox[]") List<Integer> ids,
                               @RequestHeader(value = "Referer",defaultValue = "/admin/community") String referer){
        Integer count=communityService.delete(ids);
        model.addAttribute("message",String.format("共%d项，成功删除%d项",count,ids.size()));
        model.addAttribute("url",referer);
        return "redirection";
    }
    @PostMapping("/community/hide")
    public String hideCommunities(Model model,
                                  @RequestParam(name = "checkbox[]") List<Integer> ids,
                                  @RequestHeader(value = "Referer",defaultValue = "/admin/community") String referer){
        Integer count=communityService.changeStates(2,ids);
        model.addAttribute("message",String.format("共%d项，成功隐藏%d项",count,ids.size()));
        model.addAttribute("url",referer);
        return "redirection";
    }
    @GetMapping("/user")
    public String userManager(Authentication authentication,
                              Model model,
                              @RequestParam(value = "page",required = false,defaultValue = "0") Integer pageNumber,
                              @RequestParam(value = "search", required = false, defaultValue = "") String searchText){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user",user);
        Specification<UserAttend> specification= (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!searchText.equals("")){
                predicateList.add(criteriaBuilder.like(root.get("name"),"%"+searchText+"%"));
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return criteriaBuilder.and(predicateList.toArray(predicates));
        };
        Page<UserAttend> page=userService.getAllAttends(pageNumber,pageSizeConfig.getAdmin(),specification);
        model.addAttribute("page",page);
        model.addAttribute("users",page.getContent());
        return "admin/manage-user";
    }
    @GetMapping("/user/export")
    public void userExport(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String timeString = exportDateFormat.format(new Date());
        String fileName = URLEncoder.encode("共同体个人考勤表"+timeString, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<UserAttend> userAttends = userService.getAllAttends();
        EasyExcel.write(response.getOutputStream(), UserAttend.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("考勤表"+timeString).doWrite(userAttends);
    }
    @GetMapping("/usr/{id}/resetpwd")
    public String resetPassword(Authentication authentication, @PathVariable("id")Integer id,Model model){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("url","/admin/user/editor/"+ id);
        User user1 = userService.getById(id);
        if (user1.getPrivilege()>user.getPrivilege()){
            model.addAttribute("message","没有权限哦");
            return "redirection";
        }
        userService.resetPassword(user);
        model.addAttribute("message","重置成功");
        return "redirection";
    }
    @GetMapping("/tags")
    public String tagManage(Model model,
                            Authentication authentication){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user",user);
        model.addAttribute("tags",tagService.getAll());
        return "admin/tags";
    }
    @GetMapping("/type")
    public String typeManager(Model model,
                              Authentication authentication){
        User user=(User) authentication.getPrincipal();
        model.addAttribute("user",user);
        model.addAttribute("types",typeService.getAll());
        return "admin/type";
    }
    @PostMapping("/type/add")
    public String typeAdd(Model model,@RequestParam("name") String name){
        CommunityType communityType = new CommunityType();
        communityType.setName(name);
        try {
            typeService.save(communityType);
            model.addAttribute("url","/admin/type");
            model.addAttribute("message","添加成功");
        }catch (Exception exception){
            model.addAttribute("url","/admin/type");
            model.addAttribute("message",exception.getMessage());
        }
        return "redirection";
    }
    @GetMapping("/type/del/{id}")
    public String typeDelete(@PathVariable Integer id,Model model){
        typeService.delete(id);
        model.addAttribute("url","/admin/type");
        model.addAttribute("message","删除成功");
        return "redirection";
    }
    @GetMapping("/user/editor/{id}")
    public String userEditor(Model model,
                             Authentication authentication,
                             @PathVariable Integer id){
        User user=(User) authentication.getPrincipal();
        User targetUser = userService.getById(id);
        if (targetUser==null){
            throw new PageNotFindException();
        }
        model.addAttribute("user",user);
        model.addAttribute("targetUser",targetUser);
        return "admin/user_editor";
    }
}
