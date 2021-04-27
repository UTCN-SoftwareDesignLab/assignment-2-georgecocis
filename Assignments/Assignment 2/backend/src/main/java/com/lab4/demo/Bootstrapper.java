package com.lab4.demo;

import com.lab4.demo.item.ItemRepository;
import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final ItemRepository itemRepository;

    private final UserService userService;

    private final ItemService itemService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            itemRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }

            createUsers();
            createItems();
        }
    }

    void createUsers(){
        authService.register(SignupRequest.builder()
                .email("ccs@email.com")
                .username("cocis")
                .password("password")
                .roles(Set.of("ADMIN"))
                .build());

        authService.register(SignupRequest.builder()
                .email("george@email.com")
                .username("george")
                .password("password")
                .roles(Set.of("EMPLOYEE"))
                .build());
    }

    void createItems(){
        itemService.create(ItemDTO.builder()
                .title("BookOne")
                .author("AuthorOne")
                .genre("Horror")
                .price(12.5f)
                .quantity(10)
                .build());

        itemService.create(ItemDTO.builder()
                .title("BookTwo")
                .author("AuthorOne")
                .genre("Drama")
                .price(37.9f)
                .quantity(15)
                .build());

        itemService.create(ItemDTO.builder()
                .title("BookThree")
                .author("AuthorTwo")
                .genre("Science Fiction")
                .price(27.9f)
                .quantity(0)
                .build());

        itemService.create(ItemDTO.builder()
                .title("BookFour")
                .author("AuthorThree")
                .genre("Horror")
                .price(45.9f)
                .quantity(0)
                .build());
    }
}
