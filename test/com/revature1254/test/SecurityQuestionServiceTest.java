package com.revature1254.test;



import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature1254.dao.SecurityQuestionDAO;
import com.revature1254.model.SecurityQuestion;
import com.revature1254.service.SecurityQuestionService;

@RunWith(MockitoJUnitRunner.class)
public class SecurityQuestionServiceTest {

    @Mock
    private SecurityQuestionDAO securityQuestionDAO;

    @Before
    public void setUp() {
        SecurityQuestionService.setSecurityQuestionDAO(securityQuestionDAO);
    }

    // ================= addQuestion() =================

    @Test
    public void testAddQuestion() {
        SecurityQuestionService.addQuestion(
                "What is your pet name?", "tommy", 1);

        verify(securityQuestionDAO).save(any(SecurityQuestion.class));
    }

    // ================= getQuestionsByUser() =================

    @Test
    public void testGetQuestionsByUser() {
        List<SecurityQuestion> mockList = Arrays.asList(
                new SecurityQuestion(1, "Q1", "A1", 1),
                new SecurityQuestion(2, "Q2", "A2", 1)
        );

        when(securityQuestionDAO.findByUser(1)).thenReturn(mockList);

        List<SecurityQuestion> result =
                SecurityQuestionService.getQuestionsByUser(1);

        assertEquals(2, result.size());
        verify(securityQuestionDAO).findByUser(1);
    }

    // ================= updateAnswer() =================

    @Test
    public void testUpdateAnswer_Success() {
        when(securityQuestionDAO.updateAnswer(1, 1, "newAnswer"))
                .thenReturn(true);

        boolean result =
                SecurityQuestionService.updateAnswer(1, 1, "newAnswer");

        assertTrue(result);
        verify(securityQuestionDAO)
                .updateAnswer(1, 1, "newAnswer");
    }

    @Test
    public void testUpdateAnswer_Failure() {
        when(securityQuestionDAO.updateAnswer(1, 1, "newAnswer"))
                .thenReturn(false);

        boolean result =
                SecurityQuestionService.updateAnswer(1, 1, "newAnswer");

        assertFalse(result);
        verify(securityQuestionDAO)
                .updateAnswer(1, 1, "newAnswer");
    }

    // ================= deleteQuestion() =================

    @Test
    public void testDeleteQuestion_Success() {
        when(securityQuestionDAO.delete(1, 1))
                .thenReturn(true);

        boolean result =
                SecurityQuestionService.deleteQuestion(1, 1);

        assertTrue(result);
        verify(securityQuestionDAO).delete(1, 1);
    }

    @Test
    public void testDeleteQuestion_Failure() {
        when(securityQuestionDAO.delete(1, 1))
                .thenReturn(false);

        boolean result =
                SecurityQuestionService.deleteQuestion(1, 1);

        assertFalse(result);
        verify(securityQuestionDAO).delete(1, 1);
    }
}
