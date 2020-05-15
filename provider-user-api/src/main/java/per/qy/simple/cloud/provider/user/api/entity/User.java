package per.qy.simple.cloud.provider.user.api.entity;

import lombok.Data;
import per.qy.simple.cloud.common.base.entity.BaseEntity;

@Data
public class User extends BaseEntity {

    private String username;
    private String password;
    private String phone;
    private String email;
    private String description;
    private int state;
}
