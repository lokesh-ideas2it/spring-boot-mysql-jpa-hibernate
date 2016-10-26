package com.devero.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devero.dao.UserDao;
import com.devero.entity.UserEntity;
import com.devero.models.User;
import com.devero.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity create(UserEntity userEntity) {
        User user = new User();
        user.setEmail(userEntity.getEmail());
        user.setName(userEntity.getName());
        userDao.create(user);
        userEntity.setId(user.getId());
        return userEntity;
    }

    @Override
    public UserEntity getByUserId(long userId) {
        User user = userDao.getById(userId);
        if(user == null) return null;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        User user = userDao.getById(userEntity.getId());
        if(user == null) return null;
        user = new User();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setName(userEntity.getName());
        userDao.update(user);
        return userEntity;

    }
    
    @Override
    public void delete(long id) {
        User user = userDao.getById(id);
        if(user != null) {
            userDao.delete(user);
        }
    }
}
