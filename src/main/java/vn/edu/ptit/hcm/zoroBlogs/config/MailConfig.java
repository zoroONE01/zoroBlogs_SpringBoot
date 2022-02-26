/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author zoroONE01
 */
@Configuration
public class MailConfig {

    @Bean
    public JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.googlemail.com");
        mailSender.setPort(587);

        mailSender.setUsername("lekimnguyen671@gmail.com");
        mailSender.setPassword("TU1adgjmptw0");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.starttls.enable", "true"); //TLS

        return mailSender;
    }

    private static class JavaMailSender {

        public JavaMailSender() {
        }
    }

}
