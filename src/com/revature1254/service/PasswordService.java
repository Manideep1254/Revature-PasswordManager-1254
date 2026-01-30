package com.revature1254.service;

import java.util.List;

import com.revature1254.dao.PasswordEntryDAO;
import com.revature1254.daoimpl.PasswordEntryDAOImpl;
import com.revature1254.model.PasswordEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordService {

    private static PasswordEntryDAO dao = new PasswordEntryDAOImpl();

    private static final Logger logger =
            LoggerFactory.getLogger(PasswordService.class);

    public static void setPasswordEntryDAO(PasswordEntryDAO mockDao) {
        logger.debug("Injecting PasswordEntryDAO implementation");
        dao = mockDao;
    }

    public static void addPassword(String account, String username,
                                   String password, int userId) {

        logger.info("Adding password entry started for userId={} account={}",
                userId, account);

        try {
            dao.save(new PasswordEntry(0, account, username, password, userId));

            logger.info("Password entry added successfully for userId={} account={}",
                    userId, account);

        } catch (Exception e) {
            logger.error("Failed to add password entry for userId={} account={}",
                    userId, account, e);
        }
    }

    public static List<PasswordEntry> getPasswordsByUser(int userId) {
        logger.info("Fetching password entries for userId={}", userId);

        try {
            List<PasswordEntry> list = dao.findByUser(userId);

            logger.info("Fetched {} password entries for userId={}",
                    list.size(), userId);

            return list;

        } catch (Exception e) {
            logger.error("Error while fetching password entries for userId={}",
                    userId, e);
            return null;
        }
    }

    public static PasswordEntry getPasswordById(int entryId, int userId) {
        logger.info("Fetching password entry started entryId={} userId={}",
                entryId, userId);

        try {
            PasswordEntry entry = dao.findById(entryId, userId);

            if (entry == null) {
                logger.warn("Password entry not found entryId={} userId={}",
                        entryId, userId);
            } else {
                logger.info("Password entry found entryId={} userId={}",
                        entryId, userId);
            }

            return entry;

        } catch (Exception e) {
            logger.error("Error while fetching password entry entryId={} userId={}",
                    entryId, userId, e);
            return null;
        }
    }

    public static void updatePassword(int entryId, String newPassword) {
        logger.info("Updating password started for entryId={}", entryId);

        try {
            dao.updatePassword(entryId, newPassword);

            logger.info("Password updated successfully for entryId={}", entryId);

        } catch (Exception e) {
            logger.error("Failed to update password for entryId={}", entryId, e);
        }
    }

    public static void deletePassword(int entryId, int userId) {
        logger.warn("Deleting password entry started entryId={} userId={}",
                entryId, userId);

        try {
            dao.delete(entryId, userId);

            logger.info("Password entry deleted successfully entryId={} userId={}",
                    entryId, userId);

        } catch (Exception e) {
            logger.error("Failed to delete password entry entryId={} userId={}",
                    entryId, userId, e);
        }
    }

    public static List<PasswordEntry> search(String keyword, int userId) {
        logger.info("Searching password entries userId={} keyword={}",
                userId, keyword);

        try {
            List<PasswordEntry> results =
                    dao.searchByAccount(keyword, userId);

            logger.info("Search completed userId={} resultsFound={}",
                    userId, results.size());

            return results;

        } catch (Exception e) {
            logger.error("Error during search userId={} keyword={}",
                    userId, keyword, e);
            return null;
        }
    }
}