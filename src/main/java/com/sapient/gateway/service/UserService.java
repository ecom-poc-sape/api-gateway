package com.sapient.gateway.service;

import com.sapient.gateway.payload.UserDto;
import com.sapient.gateway.model.UserDetails;

import java.util.List;

public interface UserService {

    UserDetails save(UserDto user);
    List<UserDetails> findAll();
    void delete(long id);
    UserDetails findOne(String username);

    UserDetails findById(Long id);
}
