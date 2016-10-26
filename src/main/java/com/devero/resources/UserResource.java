package com.devero.resources;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devero.entity.UserEntity;
import com.devero.service.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    /**
     * Create a new user with an auto-generated id and email and name as passed
     * values.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity create(@RequestBody UserEntity userEntity) {
        userEntity = userService.create(userEntity);
        return userEntity;
    }

    /**
     * Retrieve the id for the user with the passed email address.
     */
    @RequestMapping(value = "/id/{userId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity getByUserId(@PathVariable("userId") long userId) {
        UserEntity userEntity = userService.getByUserId(userId);
        return userEntity;
    }

    /**
     * Update the email and the name for the user indentified by the passed id.
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity update(@RequestBody UserEntity userEntity) {
        userEntity = userService.update(userEntity);
        return userEntity;
    }

    /**
     * Delete the user with the passed id.
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> delete(@PathVariable("userId") long userId) {
        userService.delete(userId);
        Map<String, String> map = new HashMap<>();
        map.put("status", "User deleted succesfully");
        return map;
    }
}
