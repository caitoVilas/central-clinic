package com.clinic.notificationservice.notificationservice.services.impl;

import com.clinic.commonservice.exceptions.EmailSendingException;
import com.clinic.commonservice.logs.WriteLog;
import com.clinic.notificationservice.notificationservice.services.contracts.MailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender{
    private final JavaMailSenderImpl mailSender;
    @Value("${application.email}")
    private String email;
    private final String ERROR_MESSAGE = "no se pudo enviar el e-mail";

    /**
     * Sends a simple email to the specified recipients.
     *
     * @param to      Array of recipient email addresses.
     * @param subject Subject of the email.
     * @param body    Body content of the email.
     * @throws EmailSendingException if there is an error while sending the email.
     */
    @Override
    public void sendEmail(String[] to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        }catch (MailException e){
            log.error(WriteLog.logError(ERROR_MESSAGE));
            throw new EmailSendingException(ERROR_MESSAGE);
        }

    }

    @Override
    public void sendEmailWithAttachment(String[] to, String subject, String body, File file) {

    }

    /**
     * Sends an email using a template to the specified recipients.
     * The template is populated with the provided data.
     *
     * @param to           Array of recipient email addresses.
     * @param subject      Subject of the email.
     * @param templateName Name of the template file located in the classpath.
     * @param data         Map containing key-value pairs to replace placeholders in the template.
     * @throws EmailSendingException if there is an error while sending the email or loading the template.
     */
    @Override
    public void sendEmailWithTemplate(String[] to, String subject, String templateName, Map<String, String> data) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name() );
            helper.setFrom(email);
            helper.setTo(to);
            helper.setSubject(subject);
            String template = getTemplate(templateName);
            for (Map.Entry<String, String> entry : data.entrySet()) {
                template = template.replace("{$" + entry.getKey() + "}", entry.getValue());
            }
        }catch (MailException e){
            log.error(WriteLog.logError(ERROR_MESSAGE));
            throw new EmailSendingException(ERROR_MESSAGE);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTemplate(String templateName){
        ClassPathResource resource = new ClassPathResource(templateName);
        try {
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        }catch (IOException e){
            String ERROR_TEMPLATE = "no se pudo cargar el template";
            log.error(WriteLog.logError(ERROR_TEMPLATE));
            throw new EmailSendingException(ERROR_TEMPLATE);
        }
    }

}
