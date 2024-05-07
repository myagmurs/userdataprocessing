package org.example.service;

import org.example.model.User;
import org.example.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSampleUsers() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setInfo("{\"name\":\"John Doe\"}");

        User user2 = new User();
        user2.setId(2L);
        user2.setInfo("{\"name\":\"Jane Doe\"}");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userService.saveSampleUsers("{\"results\": [{\"name\":\"John Doe\"}, {\"name\":\"Jane Doe\"}]}");

        assertEquals(2, result.size());
        assertEquals(user1, result.get(0));
        assertEquals(user2, result.get(1));
    }
}