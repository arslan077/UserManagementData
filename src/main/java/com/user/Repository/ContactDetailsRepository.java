package com.user.Repository;

import com.user.Entity.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails,Integer> {



}