package com.mehdi.SysManagment.sercives;

import jakarta.mail.MessagingException;

public interface EmailService {

	void sendEmailWithToken(String userEmail, String link) throws MessagingException;
}
