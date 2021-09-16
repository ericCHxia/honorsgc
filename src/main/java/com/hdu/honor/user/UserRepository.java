package com.hdu.honor.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
    User getUserById(int id);
    User getUserByNum(String num);
    Long countUsersByPrivilegeGreaterThan(int privilege);
}
