/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityService.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 下午8:30
 */

package com.hdu.honor.community;

import com.hdu.honor.community.participant.Participant;
import com.hdu.honor.community.type.CommunityType;
import com.hdu.honor.community.type.CommunityTypeService;
import com.hdu.honor.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private CommunityUserRepository userRepository;
    @Autowired
    private CommunityTypeService typeService;
    @Autowired
    private CommunityUserAttendRepository communityUserAttendRepository;
    public Community get(int id){
        return communityRepository.getCommunityById(id);
    }
    public long count(){
        return communityRepository.count();
    }
    public List<Community> getAll(){
        return communityRepository.findAll();
    }
    public List<Community> getByParticipant(int id,int type){
        return communityRepository.getAllByParticipantId(id,type);
    }
    public Page<Community> getAll(int pageNumber,int pageSize){
        Sort sort = Sort.by(Sort.Direction.DESC,"tim");
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return communityRepository.findAll(pageable);
    }
    public Page<Community> getAll(int pageNumber, int pageSize, Specification<Community> specification){
        Sort sort = Sort.by(Sort.Direction.DESC,"tim");
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return communityRepository.findAll(specification,pageable);
    }
    public List<Integer> getIdsByParticipant(int id,List<Integer> type){
        return communityRepository.getCommunitiesIdByParticipantId(id,type);
    }
    /**
     * 保存/添加共同体
     * @return 如果保存失败会返回null
     */
    public Community save(Community community){
        return communityRepository.saveAndFlush(community);
    }
    public Community save(String title, String description, String detail,
                          int typeId,User user, String img, Integer lmt){
        CommunityType type = typeService.get(typeId);
        Community community = new Community(title,description,detail,type,user,img,lmt);
        return save(community);
    }
    public Integer delete(int id){
        return communityRepository.deleteCommunityById(id);
    }
    public Integer delete(List<Integer> ids){
        return communityRepository.deleteCommunitiesByIdIn(ids);
    }
    public Integer changeStates(Integer state,List<Integer> ids){
        return communityRepository.updateStateByIds(state,ids);
    }

    public List<CommunityUserAttend> getAttend(Community community){
        return communityUserAttendRepository.findCommunityUserAttendsByCommunity(community);
    }
    public static Participant user2participant(User user,Integer communityId,Integer type){
        Participant participant = new Participant();
        participant.setType(type);
        participant.setCommunityId(communityId);
        participant.setUser(user);
        return participant;
    }
    public static List<Participant> user2participant(Collection<User> users,Integer communityId,Integer type){
        List<Participant> participants = new ArrayList<>();
        for (User user :
                users) {
            participants.add(user2participant(user,communityId,type));
        }
        return participants;
    }
    public static List<Participant> user2participant(Collection<User> users,Community community,Integer type){
        return user2participant(users,community.getId(),type);
    }
}
