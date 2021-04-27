package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.UrlMapping;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.ERole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

//    @Test
//    void createUser() throws Exception {
//        UserListDTO dto = TestCreationFactory.newUserListDTO();
//        when(userService.create(dto)).thenReturn(dto);
//
//        ResultActions result = performPostWithRequestBody(USERS, dto);
//        result.andExpect(status().isOk())
//                .andExpect(jsonContentToBe(dto));
//    }

//    @Test
//    void edit() throws Exception {
//        long id = randomLong();
//        UserListDTO userListDTO = TestCreationFactory.newUserListDTO();
//
//        when(userService.updateUser(id, userListDTO)).thenReturn(userListDTO);
//
//        ResultActions result = performPatchWithRequestBodyAndPathVariable(USERS + ENTITY, userListDTO, id);
//        result.andExpect(status().isOk())
//                .andExpect(jsonContentToBe(userListDTO));
//    }

    @Test
    void getItem() throws Exception {
        long id = randomLong();
        UserListDTO reqItem = TestCreationFactory.newUserListDTO();
        when(userService.get(id)).thenReturn(reqItem);

        ResultActions result = performGetWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }
}