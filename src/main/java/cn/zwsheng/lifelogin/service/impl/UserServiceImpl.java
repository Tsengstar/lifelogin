package cn.zwsheng.lifelogin.service.impl;

import cn.zwsheng.lifelogin.domain.UserDTO;
import cn.zwsheng.lifelogin.entity.User;
import cn.zwsheng.lifelogin.repository.UserRepository;
import cn.zwsheng.lifelogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Long addUser(UserDTO userDTO) {

        if (repository.findByUsername(userDTO.getUsername()) != null){
            return -1L;
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userDTO.getPassword()));
        repository.saveAndFlush(user);

        return user.getId();
    }

    @Override
    public UserDTO findUserById(Long id) {
        UserDTO result = new UserDTO();
        Optional<User> userOptional = repository.findById(id);

        if (!userOptional.isPresent()){
            return result;
        }

        User user = userOptional.get();

        result.setUsername(user.getUsername());
        result.setPassword(user.getPassword());

        return result;
    }

    @Override
    public UserDTO findUserByName(String name) {

        User user = repository.findByUsername(name);

        UserDTO result = new UserDTO();
        result.setUsername(user.getUsername());
        result.setPassword(user.getPassword());

        return result;
    }

    @Override
    public List<UserDTO> findAllUser() {
        List<User> list = repository.findAll();
        List<UserDTO> result = new ArrayList<>();
        list.forEach(l->{
            
        });

        return null;
    }

}
