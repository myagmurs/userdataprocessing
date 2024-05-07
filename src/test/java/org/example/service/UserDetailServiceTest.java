package org.example.service;

import org.example.model.UserDetail;
import org.example.model.UserDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserDetailServiceTest {

    @InjectMocks
    private UserDetailService userDetailService;

    @Mock
    private UserDetailRepository userDetailRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void filterUserDetails_givenNoParamsProvided_thenReturnAllUserDetails() {
        UserDetail userDetail1 = new UserDetail();
        userDetail1.setId(1L);

        UserDetail userDetail2 = new UserDetail();
        userDetail2.setId(2L);

        when(userDetailRepository.findAll()).thenReturn(Arrays.asList(userDetail1, userDetail2));

        List<UserDetail> result = userDetailService.filterUserDetails(null);

        assertEquals(2, result.size());
        assertEquals(userDetail1, result.get(0));
        assertEquals(userDetail2, result.get(1));
    }
}
