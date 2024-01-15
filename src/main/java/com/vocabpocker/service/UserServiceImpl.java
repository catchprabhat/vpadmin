package com.vocabpocker.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vocabpocker.dao.UserDao;
import com.vocabpocker.dto.UserWrapper;
import com.vocabpocker.model.User;
import com.vocabpocker.util.HashingUtil;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	public User findById(Long id) {
		return dao.findById(id);
	}

	public void saveOrUpdate(User t) {
		if (null != t.getId()) {
			User savedEntity = findById(t.getId());
			savedEntity.setActive(t.getActive());
			savedEntity.setEmail(t.getEmail());
			savedEntity.setFullName(t.getFullName());
			//Password should be reset separately
			//savedEntity.setHashedPassword(HashingUtil.getSHA256Hash(t.getPassword()));
			savedEntity.setJoiningDate(t.getJoiningDate());
			dao.update(t);
		} else {
			LocalDate date = new LocalDate();
			t.setJoiningDate(date);
			t.setHashedPassword(HashingUtil.getSHA256Hash(t.getPassword()));
			dao.persist(t);
		}

	}

	public void deleteById(Long id) {
		dao.deleteById(id);
	}

	public Set<User> findAll() {
		return dao.findAll();
	}

	public Set<User> findAllActive() {
		return dao.findAllActive();
	}

	public boolean isActive(Long id) {
		return dao.isActive(id);
	}

	public Map<Long, User> findByIds(Collection<Long> ids) {
		Map<Long, User> usersMap = new HashMap<Long, User>();
		List<User> users = dao.findByIds(ids);

		if (!users.isEmpty()) {
			for (User user : users) {
				usersMap.put(user.getId(), user);
			}
		}
		return usersMap;
	}
	
	public UserWrapper toUserWrapper(User user) {
		UserWrapper userWrapper = new UserWrapper();
		if (null != user) {
			userWrapper.setId(user.getId());
			userWrapper.setEmail(user.getEmail());
			userWrapper.setFullName(user.getFullName());
			userWrapper
					.setJoiningDate(user.getJoiningDate().toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
			userWrapper.setActive(user.getActive());
		}
		return userWrapper;
	}

	public Set<UserWrapper> toUserWrapper(Set<User> users) {
		Set<UserWrapper> wrapperList = new LinkedHashSet<UserWrapper>();
		if (null != users && !users.isEmpty()) {
			for (User user : users) {
				wrapperList.add(toUserWrapper(user));
			}
		}
		return wrapperList;
	}
}
