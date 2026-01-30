package com.revature1254.service;

import com.revature1254.dao.UserDAO;
import com.revature1254.daoimpl.UserDAOImpl;
import com.revature1254.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

    private static UserDAO userDAO = new UserDAOImpl();

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    public static void setUserDAO(UserDAO dao) {
        logger.debug("Injecting UserDAO implementation");
        userDAO = dao;
    }

    public static User register(String name, String email, String password) {

        logger.info("User registration started for email={}", email);

        try {
            User existingUser = userDAO.findByEmail(email);

            if (existingUser != null) {
                logger.warn("Duplicate registration attempt for email={}", email);
                return null;
            }

            User user = new User(0, name, email, password);
            userDAO.save(user);

            logger.info("User registered successfully for email={}", email);
            return user;

        } catch (Exception e) {
            logger.error("Error occurred during registration for email={}", email, e);
            return null;
        }
    }

    public static User login(String email, String password) {

        logger.info("Login attempt started for email={}", email);

        try {
            User user = userDAO.findByEmail(email);

            if (user == null) {
                logger.warn("Login failed - user not found for email={}", email);
                return null;
            }

            if (user.getMasterPassword().equals(password)) {
                logger.info("User logged in successfully for email={}", email);
                return user;
            }

            logger.warn("Login failed - wrong password for email={}", email);
            return null;

        } catch (Exception e) {
            logger.error("Error occurred during login for email={}", email, e);
            return null;
        }
    }

    public static User getUserByEmail(String email) {
        logger.info("Fetching user details for email={}", email);

        try {
            User user = userDAO.findByEmail(email);

            if (user == null) {
                logger.warn("User not found for email={}", email);
            }

            return user;

        } catch (Exception e) {
            logger.error("Error while fetching user for email={}", email, e);
            return null;
        }
    }

    public static void updateMasterPassword(int userId, String newPass) {
        logger.info("Updating master password for userId={}", userId);

        try {
            userDAO.updatePassword(userId, newPass);
            logger.info("Master password updated successfully for userId={}", userId);

        } catch (Exception e) {
            logger.error("Failed to update master password for userId={}", userId, e);
        }
    }

    public static void updateProfile(int userId, String name, String email) {
        logger.info("Updating profile for userId={}", userId);

        try {
            userDAO.updateProfile(userId, name, email);
            logger.info("Profile updated successfully for userId={}", userId);

        } catch (Exception e) {
            logger.error("Failed to update profile for userId={}", userId, e);
        }
    }
}