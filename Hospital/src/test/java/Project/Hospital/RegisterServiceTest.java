package Project.Hospital;

import Project.Hospital.Entities.User;
import Project.Hospital.Repositories.UserRepository;
import Project.Hospital.Service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    UserRepository userRepository;

    @InjectMocks
    RegisterService registerService;


    @Test
    public void existingUserCheckTest() {

        User user = new User();
        user.setId(1);
        user.setUsername("username");

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("username2");

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        User user3 = new User();
        user3.setUsername("username2");

        User user4 = new User();
        user4.setUsername("usr");

        Mockito.when(userRepository.findAll()).thenReturn(users);
        boolean isExistingUser = registerService.existingUserCheck(user3);
        assertTrue(isExistingUser);

        boolean isExistingUser2 = registerService.existingUserCheck(user4);
        assertFalse(isExistingUser2);
    }
}
