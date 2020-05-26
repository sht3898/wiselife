package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	void save(Integer integer);

	Category findBycategoryId(Integer integer);

}
