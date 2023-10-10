package com.user.Repository;

import com.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User,Integer> {


    User findByUserId(Long userId);


    void deleteByUserId(Long userId);
}
