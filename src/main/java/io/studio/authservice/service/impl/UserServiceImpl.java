package io.studio.authservice.service.impl;

import io.studio.authservice.mapper.UserMapper;
import io.studio.authservice.model.dto.AuthRequest;
import io.studio.authservice.model.entity.User;
import io.studio.authservice.service.UserService;
import io.studio.authservice.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/30 2:13
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserMapper userMapper ;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 注册新用户
     *
     * @param authRequest 待注册的用户
     * @return boolean 注册是否成功
     */
    @Override
    public boolean register(AuthRequest authRequest) {
        User user = new User();
        user.setPassword(authRequest.getPassword());
        user.setUsername(authRequest.getUsername());
        user.setCreateTime(TimeUtils.getTimeByNow());
        user.setUpdateTime(TimeUtils.getTimeByNow());
        user.setValid((short) 1);
        user.setIsAdmin((short) 0);
        int count = userMapper.insert(user);
        return count > 0;
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户信息
     */
    @Override
    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * 修改用户密码
     *
     * @param user 待修改密码的用户
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 密码是否修改成功
     */
    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            int count = userMapper.updatePassword(user, newPassword);
            return count > 0;
        }
        return false;
    }

    /**
     * 返回 UserDetails 对象，这是 Spring Security 自带的一个接口
     * 用于表示用户的详细信息。在返回 UserDetails 对象之前，我们需要先将用密码加密后再返回。
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), passwordEncoder.encode(user.getPassword()), Collections.emptyList());
    }
}
