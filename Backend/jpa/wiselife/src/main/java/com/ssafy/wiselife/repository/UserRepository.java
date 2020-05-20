package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.wiselife.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
