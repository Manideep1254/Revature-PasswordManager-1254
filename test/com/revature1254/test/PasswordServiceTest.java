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

import com.revature1254.dao.PasswordEntryDAO;
import com.revature1254.model.PasswordEntry;
import com.revature1254.service.PasswordService;

@RunWith(MockitoJUnitRunner.class)
public class PasswordServiceTest {

    @Mock
    private PasswordEntryDAO passwordEntryDAO;

    @Before
    public void setUp() {
        PasswordService.setPasswordEntryDAO(passwordEntryDAO);
    }

    // ================= addPassword() =================

    @Test
    public void testAddPassword() {
        PasswordService.addPassword("Gmail", "sai@gmail.com", "1234", 1);

        verify(passwordEntryDAO).save(any(PasswordEntry.class));
    }

    // ================= getPasswordsByUser() =================

    @Test
    public void testGetPasswordsByUser() {
        List<PasswordEntry> mockList = Arrays.asList(
                new PasswordEntry(1, "Gmail", "sai@gmail.com", "1234", 1),
                new PasswordEntry(2, "Github", "sai", "abcd", 1)
        );

        when(passwordEntryDAO.findByUser(1)).thenReturn(mockList);

        List<PasswordEntry> result = PasswordService.getPasswordsByUser(1);

        assertEquals(2, result.size());
        verify(passwordEntryDAO).findByUser(1);
    }

    // ================= getPasswordById() =================

    @Test
    public void testGetPasswordById_Found() {
        PasswordEntry entry =
                new PasswordEntry(1, "Gmail", "sai@gmail.com", "1234", 1);

        when(passwordEntryDAO.findById(1, 1)).thenReturn(entry);

        PasswordEntry result = PasswordService.getPasswordById(1, 1);

        assertNotNull(result);
        assertEquals("Gmail", result.getAccountName());
    }

    @Test
    public void testGetPasswordById_NotFound() {
        when(passwordEntryDAO.findById(1, 1)).thenReturn(null);

        PasswordEntry result = PasswordService.getPasswordById(1, 1);

        assertNull(result);
    }

    // ================= updatePassword() =================

    @Test
    public void testUpdatePassword() {
        PasswordService.updatePassword(1, "newpass");

        verify(passwordEntryDAO).updatePassword(1, "newpass");
    }

    // ================= deletePassword() =================

    @Test
    public void testDeletePassword() {
        PasswordService.deletePassword(1, 1);

        verify(passwordEntryDAO).delete(1, 1);
    }

    // ================= search() =================

    @Test
    public void testSearch() {
        List<PasswordEntry> mockResults = Arrays.asList(
                new PasswordEntry(1, "Gmail", "sai@gmail.com", "1234", 1)
        );

        when(passwordEntryDAO.searchByAccount("Gmail", 1))
                .thenReturn(mockResults);

        List<PasswordEntry> results =
                PasswordService.search("Gmail", 1);

        assertEquals(1, results.size());
        verify(passwordEntryDAO).searchByAccount("Gmail", 1);
    }
}
