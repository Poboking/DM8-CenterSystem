package io.studio.authservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/29 20:32
 */
//@Entity
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String username;
//    private String password;
//
//    // constructors, getters and setters
//}


@Data
public class User {
    @Id
    private long id;
    private String username;
    private String password;
    private short isAdmin;
    private short valid;
    private Timestamp createTime;
    private Timestamp updateTime;
}

