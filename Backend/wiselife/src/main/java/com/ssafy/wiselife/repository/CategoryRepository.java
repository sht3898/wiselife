package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.wiselife.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	void save(Integer integer);

	Category findBycategoryId(Integer integer);

}
