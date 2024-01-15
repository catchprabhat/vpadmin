package com.vocabpocker.dao;

import org.springframework.stereotype.Repository;

import com.vocabpocker.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

}
