package per.qy.simple.cloud.provider.user.api.model;

import lombok.Data;
import per.qy.simple.cloud.common.base.entity.BaseEntity;

@Data
public class UserDto {

    private String id;
    private String username;
    private String phone;
    private String email;
    private String description;
}
