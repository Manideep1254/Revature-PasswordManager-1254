package com.revature1254.test;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature1254.dao.UserDAO;
import com.revature1254.model.User;
import com.revature1254.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Before
    public void setUp() {
        // Inject mock DAO into service
        UserService.setUserDAO(userDAO);
    }

    // ================= register() =================

    @Test
    public void testRegister_Success() {
        // arrange
        when(userDAO.findByEmail("test@gmail.com")).thenReturn(null);

        // act
        User user = UserService.register("Sai", "test@gmail.com", "1234");

        // assert
        assertNotNull(user);
        assertEquals("Sai", user.getName());
        assertEquals("test@gmail.com", user.getEmail());

        verify(userDAO).save(any(User.class));
    }

    @Test
    public void testRegister_DuplicateEmail() {
        // arrange
        when(userDAO.findByEmail("test@gmail.com"))
                .thenReturn(new User(1, "Sai", "test@gmail.com", "1234"));

        // act
        User user = UserService.register("Sai", "test@gmail.com", "1234");

        // assert
        assertNull(user);
        verify(userDAO, never()).save(any(User.class));
    }

    // ================= login() =================

    @Test
    public void testLogin_Success() {
        // arrange
        User mockUser = new User(1, "Sai", "test@gmail.com", "1234");
        when(userDAO.findByEmail("test@gmail.com")).thenReturn(mockUser);

        // act
        User user = UserService.login("test@gmail.com", "1234");

        // assert
        assertNotNull(user);
        assertEquals("Sai", user.getName());
    }

    @Test
    public void testLogin_WrongPassword() {
        // arrange
        User mockUser = new User(1, "Sai", "test@gmail.com", "1234");
        when(userDAO.findByEmail("test@gmail.com")).thenReturn(mockUser);

        // act
        User user = UserService.login("test@gmail.com", "wrong");

        // assert
        assertNull(user);
    }

    @Test
    public void testLogin_UserNotFound() {
        // arrange
        when(userDAO.findByEmail("test@gmail.com")).thenReturn(null);

        // act
        User user = UserService.login("test@gmail.com", "1234");

        // assert
        assertNull(user);
    }

    // ================= getUserByEmail() =================

    @Test
    public void testGetUserByEmail() {
        User mockUser = new User(1, "Sai", "test@gmail.com", "1234");
        when(userDAO.findByEmail("test@gmail.com")).thenReturn(mockUser);

        User user = UserService.getUserByEmail("test@gmail.com");

        assertNotNull(user);
        verify(userDAO).findByEmail("test@gmail.com");
    }

    // ================= updateMasterPassword() =================

    @Test
    public void testUpdateMasterPassword() {
        UserService.updateMasterPassword(1, "newpass");

        verify(userDAO).updatePassword(1, "newpass");
    }

    // ================= updateProfile() =================

    @Test
    public void testUpdateProfile() {
        UserService.updateProfile(1, "NewName", "new@gmail.com");

        verify(userDAO).updateProfile(1, "NewName", "new@gmail.com");
    }
}
