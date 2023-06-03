package com.sv.service.oauth.service;

import com.sv.usercommonsservice.domain.User;

public interface IUserService {

    public User findByUserName(String userName);

    public User update(User user, Long id);
}
