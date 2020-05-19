package per.qy.simple.cloud.provider.user.service;

import per.qy.simple.cloud.provider.user.api.model.UserDto;
import per.qy.simple.cloud.provider.user.api.service.UserDaoService;

public class UserDaoServiceImpl implements UserDaoService {

    @Override
    public UserDto getUser(String id) {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setUsername("super");
        dto.setPhone("110");
        dto.setEmail("110@qq.com");
        dto.setDescription("Hello world!");
        return dto;
    }
}
