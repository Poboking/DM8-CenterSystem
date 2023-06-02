package io.studio.authservice.mapper;

import io.studio.authservice.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/30 2:45
 */
@Mapper
public interface UserMapper {
    /**
     * 插入用户数据
     * @param user
     * @return int 返回值为插入的行数,通常等于1
     */
    int insert(User user);

    /**
     * 通过用户名查找用户信息
     * @param username
     * @return User
     */
    User selectByUsername(@Param("user_name") String username);

    /**
     * 修改用户密码
     * @param user
     * @param newPassword
     * @return int 返回值为修改的行数,通常等于1
     */
    int updatePassword(@Param("user") User user, @Param("newPassword") String newPassword);
}
