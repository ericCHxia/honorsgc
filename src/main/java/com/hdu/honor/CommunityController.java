/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityController.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 上午11:01
 */

package com.hdu.honor;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.hdu.honor.community.Community;
import com.hdu.honor.community.CommunityService;
import com.hdu.honor.community.CommunityUserAttend;
import com.hdu.honor.community.participant.Participant;
import com.hdu.honor.community.participant.ParticipantType;
import com.hdu.honor.community.record.CommunityRecord;
import com.hdu.honor.community.record.CommunityRecordService;
import com.hdu.honor.community.type.CommunityType;
import com.hdu.honor.community.type.CommunityTypeService;
import com.hdu.honor.config.PageSizeConfig;
import com.hdu.honor.exception.HttpForbiddenException;
import com.hdu.honor.exception.HttpInvalidParameterException;
import com.hdu.honor.exception.PageNotFindException;
import com.hdu.honor.image.ImageUploadException;
import com.hdu.honor.image.ImgService;
import com.hdu.honor.user.User;
import com.hdu.honor.user.UserService;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/community")
public class CommunityController {
    private final Logger logger = LoggerFactory.getLogger(CommunityController.class);
    private final SimpleDateFormat exportDateFormat = new SimpleDateFormat("yyyyMMdd");
    @Autowired
    private CommunityService communityService;
    @Autowired
    private CommunityTypeService typeService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityRecordService recordService;
    @Autowired
    private PageSizeConfig pageSizeConfig;
    //Markdown解析器
    private final HtmlRenderer renderer ;
    private final Parser parser;
    public CommunityController() {
        MutableDataSet options= new MutableDataSet();
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }
    @GetMapping({"/","","/mine","/mgd","/ptcp"})
    public String index(Authentication authentication,
                        HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page",required = false,defaultValue = "0") Integer pageNumber,
                        @RequestParam(value = "search", required = false, defaultValue = "") String searchText,
                        @RequestParam(value = "typ", required = false,defaultValue = "-1") Integer type){
        User user = (User) authentication.getPrincipal();
        String title = "主页 - 卓越学院成长共同体平台";
        String url = request.getRequestURI();
        if (url.startsWith("/community/mine")){
            title = "我发布 - 卓越学院成长共同体平台";
        }else if (url.startsWith("/community/mgd")){
            title = "我指导 - 卓越学院成长共同体平台";
        }else if (url.startsWith("/community/ptcp")){
            title = "我参与 - 卓越学院成长共同体平台";
        }
        Specification<Community> spec = (root, query, cb) ->{
            List<Predicate> list = new ArrayList<>();
            if (!searchText.equals("")){
                list.add(cb.like(root.get("title"),"%"+ searchText +"%"));
            }
            if (type!=-1){
                list.add(cb.equal(root.get("type"),type));
            }
            CriteriaBuilder.In<Integer> states = cb.in(root.get("state"));
            states.value(1);
            if (url.startsWith("/community/mine")){
                list.add(cb.equal(root.get("user"),user));
                states.value(0);
                states.value(2);
            }else if (url.startsWith("/community/mgd")){
                CriteriaBuilder.In<Integer> in = cb.in(root.get("id"));
                List<Integer> types = new ArrayList<>();
                types.add(1);
                List<Integer> ids = communityService.getIdsByParticipant(user.getId(),types);
                for (Integer id : ids) {
                    in.value(id);
                }
                list.add(in);
            }else if (url.startsWith("/community/ptcp")){
                CriteriaBuilder.In<Integer> in = cb.in(root.get("id"));
                List<Integer> types = new ArrayList<>();
                types.add(0);
                types.add(1);
                List<Integer> ids = communityService.getIdsByParticipant(user.getId(),types);
                for (Integer id : ids) {
                    in.value(id);
                }
                list.add(in);
            }
            list.add(states);
            Predicate[] predicates = new Predicate[list.size()];
            return cb.and(list.toArray(predicates));
        };
        Page<Community> page=communityService.getAll(pageNumber,pageSizeConfig.getCommunity(),spec);
        List<CommunityType> types = typeService.getAll();
        model.addAttribute("title",title);
        model.addAttribute("page",page);
        model.addAttribute("types",types);
        return "community/index";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable Integer id,
                       Model model,
                       HttpServletRequest request,
                       Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Community community = communityService.get(id);
        if (community==null||!community.getUser().equals(user)&&!request.isUserInRole("ADMIN")&&community.getState()!=1){
            throw new PageNotFindException();
        }
        Node document = parser.parse(community.getDetail());
        model.addAttribute("detail",renderer.render(document));
        model.addAttribute("community",community);
        model.addAttribute("user",user);
        model.addAttribute("types",typeService.getAll());
        return "community/post";
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "typ") Integer typeId,
                       @RequestParam(name = "lmt") Integer limit,
                       @RequestParam(name = "title") String title,
                       @RequestParam(name = "dsc") String description,
                       @RequestParam(name = "detail") String detail,
                       @RequestParam(name = "file") MultipartFile file,
                       @RequestParam(name = "id",required = false,defaultValue = "-1") Integer id,
                       Authentication authentication,
                       Model model,
                       @RequestHeader(value = "Referer",defaultValue = "/community") String referer){

        //TODO: 添加访问权限控制
        String filename = "";
        if (!file.isEmpty()){
            try {
                filename = imgService.saveCover(file);
            } catch (ImageUploadException e) {
                //TODO: 修改返回的code
                model.addAttribute("url",referer);
                model.addAttribute("message",e.getMessage());
                return "redirection";
            }
        }
        CommunityType type=typeService.get(typeId);
        User user = (User) authentication.getPrincipal();
        Community community;
        if (id!=-1){
            community=communityService.get(id);
        }else {
            community=new Community();
            community.setUser(user);
        }
        if (!filename.equals("")){
            community.setImg(filename);
        }
        community.setType(type);
        community.setDescription(description);
        community.setLmt(limit);
        community.setTitle(title);
        community.setDescription(description);
        community.setDetail(detail);
        communityService.save(community);
        model.addAttribute("url","/community/post/"+ community.getId());
        model.addAttribute("message","保存成功");
        return "redirection";
    }

    @GetMapping("/add")
    public String add(Model model){
        List<CommunityType> types = typeService.getAll();
        model.addAttribute("types",types);
        return "community/addmd";
    }

    @GetMapping("/del")
    public String delete(Model model,
                         @RequestParam(name = "id") Integer id,
                         @RequestParam(name = "url",required = false,defaultValue = "/community") String url){
        if (communityService.delete(id)>0){
            model.addAttribute("message","删除成功");
        }else{
            model.addAttribute("删除失败");
        }
        model.addAttribute("url",url);
        return "redirection";
    }
    @GetMapping("/edit/{id}")
    public String edit(Model model,
                       @PathVariable Integer id,
                       Authentication authentication,
                       HttpServletRequest request){
        model.addAttribute("types",typeService.getAll());
        Community community=communityService.get(id);
        if (community==null){
            throw new PageNotFindException();
        }
        User user = (User) authentication.getPrincipal();
        if (!community.getUser().equals(user)&&!request.isUserInRole("ADMIN")){
            throw new HttpForbiddenException();
        }
        model.addAttribute("community",community);
        return "community/editmd";
    }
    @GetMapping("/post/{id}/manager")
    public String manager(@PathVariable Integer id,Model model,Authentication authentication){
        Community community=communityService.get(id);
        if (community==null){
            throw new PageNotFindException();
        }
        User user = (User) authentication.getPrincipal();
        if (!community.getUser().equals(user)&&user.getPrivilege()==0){
            throw new HttpForbiddenException();
        }
        model.addAttribute("community",community);
        return "community/manage";
    }
    @PostMapping("/post/{id}/manager")
    public String managerSubmit(@PathVariable Integer id,
                                @RequestParam("managers[]") List<Integer> managers,
                                @RequestParam("participants[]") List<Integer> participants,
                                Authentication authentication,
                                Model model){
        Community community = communityService.get(id);
        if (community==null){
            throw new PageNotFindException();
        }
        User user = (User) authentication.getPrincipal();
        if (!community.getUser().equals(user)&&user.getPrivilege()==0){
            throw new HttpForbiddenException();
        }
        Set<Participant> oldManagers = community.getManagers();
        Set<Participant> oldParticipants = community.getParticipants();

        oldManagers.retainAll(CommunityService.user2participant(userService.gets(managers),community,1));
        oldParticipants.retainAll(CommunityService.user2participant(userService.gets(participants),community,0));

        communityService.save(community);
        model.addAttribute("url",String.format("/community/post/%d",community.getId()));
        model.addAttribute("message","保存成功");
        return "redirection";
    }
    @GetMapping("/change_register")
    public String changeRegister(@RequestParam("id") Integer id,Authentication authentication,Model model){
        User user =(User) authentication.getPrincipal();
        Community community = communityService.get(id);
        if (community==null){
            throw new PageNotFindException();
        }
        if (!community.getUser().equals(user)&&user.getPrivilege()==0){
            throw new HttpForbiddenException();
        }
        community.setEnrolling(!community.getEnrolling());

        model.addAttribute("url",String.format("/community/post/%d",community.getId()));
        model.addAttribute("message","修改成功");
        return "redirection";
    }
    @GetMapping("/change_state")
    @PreAuthorize("hasRole('ADMIN')")
    public String changeState(@RequestHeader(value = "Referer",defaultValue = "/community") String referer,
                              Model model,
                              @RequestParam(name = "id") Integer id){
        Community community=communityService.get(id);
        if (community==null){
            model.addAttribute("url",referer);
            model.addAttribute("message","共同体不存在");
        }else {
            if (community.getState()==1){
                community.setState(2);
            }else {
                community.setState(1);
            }
            communityService.save(community);
            model.addAttribute("url",referer);
            model.addAttribute("message","修改成功");
        }
        return "redirection";
    }
    @GetMapping("/change")
    public String addParticipant(@RequestParam(value = "id")Integer id,
                                 @RequestParam(value = "type")Integer typeId,
                                 Authentication authentication,
                                 Model model){
        String url= String.format("/community/post/%d", id);
        Community community= communityService.get(id);
        if (community==null){
            model.addAttribute("message","共同体不存在");
            model.addAttribute("url","/community");
            return "redirection";
        }
        if (!community.getEnrolling()){
            model.addAttribute("message","共同体已停止报名");
            model.addAttribute("url","/community");
            return "redirection";
        }
        User user = (User) authentication.getPrincipal();
        if (community.isParticipant(user)){
            community.removeParticipant(user);
            communityService.save(community);
            model.addAttribute("message","你成功取消了参加该共同体");
            model.addAttribute("url",url);
            return "redirection";
        }
        if (community.isManager(user)){
            community.removeManager(user);
            communityService.save(community);
            model.addAttribute("message","你成功取消了指导该共同体");
            model.addAttribute("url",url);
            return "redirection";
        }
        if (!community.getEnrolling()){
            model.addAttribute("message","当前共同体的报名已结束哦");
            model.addAttribute("url",url);
            return "redirection";
        }
        if (community.getLmt()!=0&&typeId==0&&community.getParticipants().size()>=community.getLmt()){
            model.addAttribute("message","参与人数已达上限");
            model.addAttribute("url",url);
            return "redirection";
        }
        if (typeId==1&&community.getManagers().size()>=2){
            model.addAttribute("message","指导人数已达上限");
            model.addAttribute("url",url);
            return "redirection";
        }
        if (typeId==0||typeId==1){
            Participant participant=new Participant(user);
            participant.setType(typeId);
            if (typeId==0){
                community.getParticipants().add(participant);
            }else {
                community.getManagers().add(participant);
            }
            communityService.save(community);
            model.addAttribute("message","成功参与该共同体");
            model.addAttribute("url",url);
            return "redirection";
        }else {
            throw new HttpInvalidParameterException();
        }
    }
    @PostMapping("/record")
    public String addRecord(Authentication authentication,
                            @RequestParam(value = "detail")String detail,
                            @RequestParam(value = "file") MultipartFile file,
                            @RequestParam(value = "chk[]")List<Integer> userId,
                            @RequestParam(value = "id")Integer communityId,
                            Model model){
        Community community = communityService.get(communityId);
        User user = (User) authentication.getPrincipal();
        if (community==null){
            throw new PageNotFindException();
        }
        if (!community.isManager(user)&&!community.isParticipant(user)&&user.getPrivilege()==0){
            model.addAttribute("message","添加失败");
            model.addAttribute("url",String.format("/community/post/%d",communityId));
            return "redirection";
        }
        String fileName=null;
        if (!file.isEmpty()){
            try {
                fileName=imgService.save(file);
            } catch (ImageUploadException e) {
                e.printStackTrace();
                model.addAttribute("message",e.getMessage());
                model.addAttribute("url",String.format("/community/post/%d",communityId));
                return "redirection";
            }
        }
        CommunityRecord record=new CommunityRecord();
        record.setCommunityId(communityId);
        record.setDetail(detail);
        record.setImg(fileName);
        record.setUser(user);
        List<User> users=userService.gets(userId);
        record.setUsers(users);
        record=recordService.save(record);
        model.addAttribute("message","添加成功");
        model.addAttribute("url",String.format("/community/post/%d",communityId));
        return "redirection";
    }
    @GetMapping("/delrecord")
    public String deleteRecord(Authentication authentication,
                               Model model,
                               @RequestParam(value = "id")Integer recordId){
        User user = (User) authentication.getPrincipal();
        CommunityRecord record = recordService.get(recordId);
        if (record==null){
            throw new HttpInvalidParameterException();
        }
        if (!user.equals(record.getUser())&&user.getPrivilege()==0){
            throw new HttpInvalidParameterException();
        }
        Integer count=recordService.delete(recordId);
        model.addAttribute("url",String.format("/community/post/%d",record.getCommunityId()));
        if (count==1){
            model.addAttribute("message","删除成功");
        }else{
            model.addAttribute("message","删除失败");
        }

        return "redirection";
    }
    @GetMapping("/export/{id}")
    public void exportCommunity(Authentication authentication,HttpServletResponse response,@PathVariable("id") Integer id) throws IOException {
        Community community = communityService.get(id);
        User user = (User) authentication.getPrincipal();

        if (user.getPrivilege()==0&&!user.equals(community.getUser())&&!community.isManager(user)){
            throw new HttpForbiddenException();
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String timeString = exportDateFormat.format(new Date());
        String fileName = URLEncoder.encode(community.getTitle()+"个人考勤表"+timeString, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<CommunityUserAttend> communityUserAttends = communityService.getAttend(community);
        EasyExcel.write(response.getOutputStream(), CommunityUserAttend.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("考勤表").doWrite(communityUserAttends);
    }
}
