package per.qy.simple.cloud.provider.user.api.service;

import per.qy.simple.cloud.provider.user.api.model.UserDto;

public interface UserDaoService {

    UserDto getUser(String id);
}
