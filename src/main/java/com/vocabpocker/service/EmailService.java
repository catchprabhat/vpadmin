package com.vocabpocker.service;

import com.vocabpocker.dto.EmailDto;

public interface EmailService {

	void sendEmail(EmailDto emailDto);
}
