package org.example.controller;

import org.example.model.User;
import org.example.model.UserDetail;
import org.example.service.UserDetailService;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailService userDetailService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void getSampleUsers_whenCalled_thenReturnSampleUsers() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setInfo("{\"name\":\"Jane Doe\"}");

        User user2 = new User();
        user2.setId(2L);
        user2.setInfo("{\"name\":\"John Doe\"}");

        when(userService.saveSampleUsers(any())).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/get-sample-users")
                        .param("numberOfSamples", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"info\":\"{\\\"name\\\":\\\"Jane Doe\\\"}\"}," +
                        "{\"id\":2,\"info\":\"{\\\"name\\\":\\\"John Doe\\\"}\"}]"));
    }

    @Test
    public void getSampleUsers_whenExternalCalled_thenReturnSampleUsers() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setInfo("{\"name\":\"Jane Doe\"}");

        User user2 = new User();
        user2.setId(2L);
        user2.setInfo("{\"name\":\"John Doe\"}");

        when(userService.saveSampleUsers(any())).thenReturn(Arrays.asList(user1, user2));

        mockServer.expect(MockRestRequestMatchers.requestTo("https://randomuser.me/api/?results=2"))
                .andRespond(MockRestResponseCreators.withSuccess("[{\"name\":\"Jane Doe\"}, " +
                        "{\"name\":\"John Doe\"}]", MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/get-sample-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numberOfSamples", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"info\":\"{\\\"name\\\":\\\"Jane Doe\\\"}\"}," +
                        "{\"id\":2,\"info\":\"{\\\"name\\\":\\\"John Doe\\\"}\"}]", true));

    }

    @Test
    public void filterUserDetails_whenNoFilterProvided_thenReturnAllUserDetails() throws Exception {
        UserDetail userDetail1 = new UserDetail();
        userDetail1.setId(1L);

        UserDetail userDetail2 = new UserDetail();
        userDetail2.setId(2L);

        when(userDetailService.filterUserDetails(any())).thenReturn(Arrays.asList(userDetail1, userDetail2));

        mockMvc.perform(get("/filter-user-details")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1}, {\"id\":2}]"));
    }
}
