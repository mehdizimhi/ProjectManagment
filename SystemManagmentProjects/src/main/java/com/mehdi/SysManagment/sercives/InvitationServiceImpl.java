package com.mehdi.SysManagment.sercives;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mehdi.SysManagment.models.Invitation;
import com.mehdi.SysManagment.repositories.InvitationRepository;

import jakarta.mail.MessagingException;

@Service
public class InvitationServiceImpl implements InvitationService {

	@Autowired
	private InvitationRepository invitationRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void sendInvitation(String email, Long projectId) throws MessagingException {

		String invitationToken = UUID.randomUUID().toString();
		
		Invitation invitation = new Invitation();
		invitation.setEmail(email);
		invitation.setProjetId(projectId);
		invitation.setToken(invitationToken);
		
		invitationRepository.save(invitation);
		
		String invitationLink = "http://localhost:5173/accept_invitation?token="+invitationToken;
		
		emailService.sendEmailWithToken(email, invitationLink);
		
	}

	@Override
	public Invitation acceptInvitation(String token, Long userId) throws Exception {
		Invitation invitation = invitationRepository.findByToken(token);
		if(invitation==null) {
			throw new Exception("invalid invitation token");
		}
		return invitation;
	}

	@Override
	public String getTokenByUserMail(String userMail) {
		Invitation inv = invitationRepository.findByEmail(userMail);
		
		return inv.getToken();
	}

	@Override
	public void deleteToken(String token) {
		Invitation inv = invitationRepository.findByToken(token);
		invitationRepository.delete(inv);
		
	}

}
