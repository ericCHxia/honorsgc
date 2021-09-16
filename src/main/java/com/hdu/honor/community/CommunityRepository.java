/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityRepository.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 下午8:23
 */

package com.hdu.honor.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Integer>, JpaSpecificationExecutor<Community> {
    Page<Community> getAllByUserId(Pageable pageable,Integer id);
    List<Community> getAllByUserId(Integer id);
    Community getCommunityById(Integer id);
    Page<Community> findAllByState(Pageable pageable,Integer state);
    Page<Community> findAllByStateAndTitleLike(Pageable pageable,Integer state,String title);
    @Query(value = "SELECT * FROM gttdata WHERE gttdata.id IN (SELECT gttptcp.gttid FROM gttptcp WHERE gttptcp.usrid=:id AND gttptcp.typ=:type)",nativeQuery = true)
    List<Community> getAllByParticipantId(@Param("id") Integer id,@Param("type") Integer type);
    @Query(value = "SELECT gttptcp.gttid FROM gttptcp WHERE gttptcp.usrid=:id AND gttptcp.typ IN :type",nativeQuery = true)
    List<Integer> getCommunitiesIdByParticipantId(@Param("id") Integer id,@Param("type") List<Integer> type);
    Integer deleteCommunityById(int id);
    @Modifying
    @Query(value = "UPDATE gttdata SET honor.gttdata.stat=:state WHERE honor.gttdata.id IN :ids",nativeQuery = true)
    Integer updateStateByIds(Integer state,List<Integer> ids);
    Integer deleteCommunitiesByIdIn(List<Integer> ids);
}