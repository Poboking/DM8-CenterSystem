package io.studio.authservice.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/29 22:24
 */
@Data
public class AuthRequest {
    private String username;
    private String password;
}
