package com.user.Repository;

import com.user.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {






    void deleteByUserId(Long userId);

//    Contact findByUserId(Long userId);

    List<Contact> findAllByUserId(Long userId);


    void deleteByCId(Long cId);

//    Contact findById(Long cId);

    Contact findByCId(Long cId);
}
