package com.poly.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.model.Account;
import com.poly.model.MailInfo;
import com.poly.service.AccountService;
import com.poly.service.EmailService;



@Controller
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	EmailService emailService;
	
	@RequestMapping("/account/forgot-pass")
	public String forgot(Model model) {
		return "/user/security/forgot-pass";
	}
	
    // send email
    @SuppressWarnings("unused")
    @PostMapping("/account/forgot-pass")
    public String getNewPassword(@RequestParam("email") String email) {
        Account account = accountService.resetPassword(email);
        if (account != null) {
            String newPass = String.valueOf((int) (Math.random() * ((9999 - 1000) + 1)) + 1000);

            try {
                MailInfo mail = new MailInfo();
                mail.setTo(email);
                mail.setSubject("New password");
                mail.setBody("Dear " + account.getFullname() + ", your new password here: " + newPass);

                emailService.send(mail); // Send the email

                // Save the hashed password in the database
                accountService.updatePassword(account, newPass);

                return "user/security/login_register";
            } catch (MessagingException e) {
                System.out.println("aloooo");
                return "user/security/forgot-pass";
            }
        }
        return "user/security/forgot-pass";
    }
    
    
    


	
}
