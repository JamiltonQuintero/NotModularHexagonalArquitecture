package com.jamiltonquintero.hexagonal.infraestructure.adapter.repository;


import com.jamiltonquintero.hexagonal.infraestructure.adapter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}