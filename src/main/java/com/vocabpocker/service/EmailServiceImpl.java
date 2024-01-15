package com.vocabpocker.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.vocabpocker.dto.EmailDto;

import freemarker.template.Configuration;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	Configuration freemarkerConfiguration;

	public void sendEmail(EmailDto emailDto) {

		MimeMessagePreparator preparator = getMessagePreparator(emailDto);

		try {
			mailSender.send(preparator);
			System.out.println("Message has been sent.............................");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(ex.getMessage());
		}
	}

	private MimeMessagePreparator getMessagePreparator(final EmailDto emailDto) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

				helper.setSubject(emailDto.getSubject());
				helper.setFrom(emailDto.getFrom());
				helper.setTo(emailDto.getTo());

				Map<String, Object> model = new HashMap<String, Object>();
				model.put("data", emailDto.getData());

				String text = geFreeMarkerTemplateContent(model, emailDto.getEmailTemplate());
				// System.out.println("Template content : " + text);

				// use the true flag to indicate you need a multipart message
				helper.setText(text, true);

				// Additionally, let's add a resource as an attachment as well.
				// helper.addAttachment("be_original.jpg", new
				// ClassPathResource("be_original.jpg"));
				// FileSystemResource file = new FileSystemResource(new
				// File("/be_original.jpg"));
				// helper.addAttachment("be_original.jpg", file);
			}
		};
		return preparator;
	}

	public String geFreeMarkerTemplateContent(Map<String, Object> model, String emailTemplate) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(FreeMarkerTemplateUtils
					.processTemplateIntoString(freemarkerConfiguration.getTemplate(emailTemplate), model));
			return content.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured while processing fmtemplate:" + e.getMessage());
		}
		return "";
	}
}
