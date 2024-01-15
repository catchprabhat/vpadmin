package com.vocabpocker.service;

import java.util.Set;

import com.vocabpocker.dto.UserWrapper;
import com.vocabpocker.model.User;

public interface UserService extends BaseService<User> {
	UserWrapper toUserWrapper(User user);
	Set<UserWrapper> toUserWrapper(Set<User> users);
}
