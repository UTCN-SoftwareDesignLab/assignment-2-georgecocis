package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.USERS;
import static com.lab4.demo.UrlMapping.ENTITY;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public UserListDTO createUser(@RequestBody UserListDTO userListDTO){
        return userService.create(userListDTO);
    }

    @PatchMapping(ENTITY)
    public UserListDTO updateUser(@PathVariable Long id, @RequestBody UserListDTO userListDTO){
        return userService.updateUser(id, userListDTO);
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @GetMapping(ENTITY)
    public UserListDTO getUser(@PathVariable Long id){
        return userService.get(id);
    }
}
