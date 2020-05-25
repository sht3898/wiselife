package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public int countByuid(long uid);
}
