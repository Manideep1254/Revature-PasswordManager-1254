package com.revature1254.service;

import java.util.List;

import com.revature1254.dao.SecurityQuestionDAO;
import com.revature1254.daoimpl.SecurityQuestionDAOImpl;
import com.revature1254.model.SecurityQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityQuestionService {

    private static SecurityQuestionDAO dao = new SecurityQuestionDAOImpl();

    private static final Logger logger =
            LoggerFactory.getLogger(SecurityQuestionService.class);

    public static void setSecurityQuestionDAO(SecurityQuestionDAO mockDao) {
        logger.debug("Injecting SecurityQuestionDAO implementation");
        dao = mockDao;
    }

    public static void addQuestion(String question, String answer, int userId) {
        logger.info("Adding security question started for userId={}", userId);

        try {
            SecurityQuestion q =
                    new SecurityQuestion(0, question, answer, userId);

            dao.save(q);

            logger.info("Security question added successfully for userId={}",
                    userId);

        } catch (Exception e) {
            logger.error("Failed to add security question for userId={}",
                    userId, e);
        }
    }

    public static List<SecurityQuestion> getQuestionsByUser(int userId) {
        logger.info("Fetching security questions for userId={}", userId);

        try {
            List<SecurityQuestion> list = dao.findByUser(userId);

            logger.info("Fetched {} security questions for userId={}",
                    list.size(), userId);

            return list;

        } catch (Exception e) {
            logger.error("Error while fetching security questions for userId={}",
                    userId, e);
            return null;
        }
    }

    public static boolean updateAnswer(int questionId, int userId,
                                       String newAnswer) {

        logger.info("Updating security question answer started questionId={} userId={}",
                questionId, userId);

        try {
            boolean updated =
                    dao.updateAnswer(questionId, userId, newAnswer);

            if (updated) {
                logger.info("Security question updated successfully questionId={}",
                        questionId);
            } else {
                logger.warn("Security question update failed questionId={} userId={}",
                        questionId, userId);
            }

            return updated;

        } catch (Exception e) {
            logger.error("Error while updating security question questionId={} userId={}",
                    questionId, userId, e);
            return false;
        }
    }

    public static boolean deleteQuestion(int questionId, int userId) {
        logger.warn("Deleting security question started questionId={} userId={}",
                questionId, userId);

        try {
            boolean deleted = dao.delete(questionId, userId);

            if (deleted) {
                logger.info("Security question deleted successfully questionId={}",
                        questionId);
            } else {
                logger.warn("Security question delete failed questionId={} userId={}",
                        questionId, userId);
            }

            return deleted;

        } catch (Exception e) {
            logger.error("Error while deleting security question questionId={} userId={}",
                    questionId, userId, e);
            return false;
        }
    }
}