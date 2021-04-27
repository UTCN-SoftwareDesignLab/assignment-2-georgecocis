package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserListDTO create(UserListDTO userListDTO){
        Role defaultRole = roleRepository.findByName(ERole.EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
        User user1 = userMapper.userFromUserListDto(userListDTO);
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        user1.setRoles(roles);
        user1.setPassword(encoder.encode(user1.getPassword()));
        return userMapper.userListDtoFromUser(userRepository.save(user1));
    }

    public UserListDTO updateUser(Long id, UserListDTO userListDTO){
        User user = findById(id);
        user.setUsername(userListDTO.getName());
        user.setPassword(encoder.encode(userListDTO.getPassword()));
        user.setEmail(userListDTO.getEmail());
        return userMapper.userListDtoFromUser(userRepository.save(user));
    }

    public void deleteUser(Long id){
        userRepository.delete(userRepository.getOne(id));
    }

    public UserListDTO get(Long id) {
        return userMapper.userListDtoFromUser(findById(id));
    }

}
