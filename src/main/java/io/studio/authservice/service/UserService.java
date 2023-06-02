package io.studio.authservice.service;

import io.studio.authservice.model.dto.AuthRequest;
import io.studio.authservice.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/30 2:52
 */
public interface UserService {
    /**
     * 注册用户
     *
     * @param authRequest
     * @return
     */
    boolean register(AuthRequest authRequest);


    /**
     * 登录用户
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);
}
