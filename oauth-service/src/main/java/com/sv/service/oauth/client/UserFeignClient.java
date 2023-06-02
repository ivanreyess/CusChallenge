package com.sv.service.oauth.client;

import com.sv.usercommonsservice.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface UserFeignClient {

    @GetMapping("api/users/search/findUserName")
    public User findByUserName(@RequestParam String userName);

}
