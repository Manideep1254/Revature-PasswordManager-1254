package com.revature1254.dao;

import java.util.List;
import com.revature1254.model.PasswordEntry;

public interface PasswordEntryDAO {

	void save(PasswordEntry entry);

	List<PasswordEntry> findByUser(int userId);

	PasswordEntry findById(int entryId, int userId);

	void updatePassword(int entryId, String newPassword);

	void delete(int entryId, int userId);

	List<PasswordEntry> searchByAccount(String keyword, int userId);
}
