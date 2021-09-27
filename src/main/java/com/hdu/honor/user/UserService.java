package com.hdu.honor.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    private UserAttendRepository userAttendRepository;
    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userRepository.getUserByNum(s);
        if (user==null) {
            throw new UsernameNotFoundException("学号不存在");
        }
        return user;
    }
    public User getByNum(String num){
        return userRepository.getUserByNum(num);
    }
    public User getById(int id){
        return userRepository.getUserById(id);
    }
    public User save(User user){
        return userRepository.saveAndFlush(user);
    }
    public long count(){
        return userRepository.count();
    }
    public Long countAdmin(){
        return userRepository.countUsersByPrivilegeGreaterThan(0);
    }
    public Page<User> getAll(Integer pageNumber, Integer pageSize, Specification<User> userSpecification){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return userRepository.findAll(userSpecification,pageable);
    }
    public Page<UserAttend> getAllAttends(Integer pageNumber, Integer pageSize, Specification<UserAttend> specification){
        Sort sort = Sort.by(Sort.Direction.ASC,"id");
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return userAttendRepository.findAll(specification,pageable);
    }
    public List<UserAttend> getAllAttends(){
        return userAttendRepository.findAll();
    }
    public List<User> gets(List<Integer> userIds){
        return userRepository.findAllById(userIds);
    }

    public static void flushUser(User user){
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }
}
