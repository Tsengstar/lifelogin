package cn.zwsheng.lifelogin.service;

import cn.zwsheng.lifelogin.domain.UserDTO;

import java.util.List;

public interface UserService {

    Long addUser(UserDTO user);

    UserDTO findUserById(Long id);

    UserDTO findUserByName(String name);

    List<UserDTO> findAllUser();

}
