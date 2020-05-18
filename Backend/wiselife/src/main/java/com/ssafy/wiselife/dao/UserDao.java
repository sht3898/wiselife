package com.ssafy.wiselife.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.model.User;

@Repository
public class UserDao {
	String ns = "wiselife.user.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public int signUp(User user) {
		return sqlSession.insert(ns+"signUp",user);
	}
	
}
