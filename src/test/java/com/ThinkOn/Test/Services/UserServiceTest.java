package com.ThinkOn.Test.Services;

import com.ThinkOn.Test.Models.User;
import com.ThinkOn.Test.Repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User("jdoe", "John", "Doe", "jdoe@example.com", "123-456-7890");
        user1.setId(1L);

        user2 = new User("asmith", "Alice", "Smith", "asmith@example.com", "098-765-4321");
        user2.setId(2L);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User createdUser = userService.createUser(user1);

        assertNotNull(createdUser);
        assertEquals("jdoe", createdUser.getUsername());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Optional<User> userOptional = userService.getUserById(1L);

        assertTrue(userOptional.isPresent());
        assertEquals("jdoe", userOptional.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User updatedDetails = new User("jdoe", "Johnny", "Doe", "johnny.doe@example.com", "111-222-3333");
        User updatedUser = userService.updateUser(1L, updatedDetails);

        assertEquals("Johnny", updatedUser.getFirstName());
        assertEquals("johnny.doe@example.com", updatedUser.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
