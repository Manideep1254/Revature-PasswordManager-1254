package com.revature1254.test;



import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature1254.dao.OTPDAO;
import com.revature1254.service.OTPService;

@RunWith(MockitoJUnitRunner.class)
public class OTPServiceTest {

    @Mock
    private OTPDAO otpDAO;

    @Before
    public void setUp() {
        OTPService.setOTPDAO(otpDAO);
    }

    // ================= generateOTP() =================

    @Test
    public void testGenerateOTP() {
        int userId = 1;

        String otp = OTPService.generateOTP(userId);

        assertNotNull(otp);
        assertEquals(4, otp.length());
        assertTrue(otp.matches("\\d{4}"));

        verify(otpDAO).saveOTP(
                eq(userId),
                eq(otp),
                anyLong()
        );
    }

    // ================= validateOTP() =================

    @Test
    public void testValidateOTP_Success() {
        when(otpDAO.validateOTP(1, "1234")).thenReturn(true);

        boolean result = OTPService.validateOTP(1, "1234");

        assertTrue(result);
        verify(otpDAO).validateOTP(1, "1234");
    }

    @Test
    public void testValidateOTP_Failure() {
        when(otpDAO.validateOTP(1, "1234")).thenReturn(false);

        boolean result = OTPService.validateOTP(1, "1234");

        assertFalse(result);
        verify(otpDAO).validateOTP(1, "1234");
    }
}
