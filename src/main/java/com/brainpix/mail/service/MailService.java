package com.brainpix.mail.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.brainpix.api.code.error.MailErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.mail.dto.SendMailDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender javaMailSender;

	public void sendMail(SendMailDto request) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false , "UTF-8");
			mimeMessageHelper.setTo(request.getReceiverEmail());
			mimeMessageHelper.setSubject(request.getTitle());
			mimeMessageHelper.setText(request.getSignupCode(), false);

			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new BrainPixException(MailErrorCode.MAIL_SEND_FAILED);
		}
	}
}
