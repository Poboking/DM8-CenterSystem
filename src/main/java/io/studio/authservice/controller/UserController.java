package io.studio.authservice.controller;

import io.studio.authservice.model.dto.AuthRequest;
import io.studio.authservice.model.entity.User;
import io.studio.authservice.service.impl.UserServiceImpl;
import io.studio.authservice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/30 2:12
 */
@RestController("/user")
public class UserController {
    private final UserServiceImpl userServiceImpl;



    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * 返回注册界面
     * @return
     */
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    /**
     * 返回登录界面
     * @return
     */
    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    /**
     * 注册用户
     *
     * @param authRequest 待注册的用户信息
     * @return HTTP 响应实体对象
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) {
        boolean success = userServiceImpl.register(authRequest);
        if (success) {
            return ResponseEntity.ok("Registration success");
            /**
             * 等价于 new ResponseEntity<>("Registration success", HtttpStatus.ok);但前者更为简洁,官方推荐前者.
             */
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return HTTP 响应实体对象
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userServiceImpl.login(username, password);
        if (user != null) {
            String token = JwtUtils.generateToken(username);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }

    /**
     * 修改用户密码
     *
     * @param user 待修改密码的用户
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return HTTP 响应实体对象
     */
    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody User user, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        boolean success = userServiceImpl.changePassword(user, oldPassword, newPassword);
        if (success) {
            return ResponseEntity.ok("Password changed");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password change failed");
        }
    }
}
