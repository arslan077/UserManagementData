package com.user.Repository;

import com.user.Entity.User;
import com.user.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {




    void deleteByUserId(int userId);

    void deleteByUserId(Long userId);

    Vehicle findByUserId(Long userId);

    List<Vehicle> findAllByUserId(Long userId);


    void deleteByVId(Long VId);

    Vehicle findByVId(Long vId);
}