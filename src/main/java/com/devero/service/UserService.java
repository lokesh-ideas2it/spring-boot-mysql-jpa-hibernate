package com.devero.service;

import com.devero.entity.UserEntity;

public interface UserService {

    public UserEntity create(UserEntity userEntity);

    public UserEntity getByUserId(long userId);

    public UserEntity update(UserEntity userEntity);

    public void delete(long id);

}
