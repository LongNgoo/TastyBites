package com.poly.service;

import javax.mail.MessagingException;

import com.poly.model.Account;
import com.poly.model.MailInfo;



public interface EmailService {
	/*
	 *		Gửi email
	 *		mail: thông tin email
	 *		MessagingException lỗi gửi email
	 * 
	 */
	public void send(MailInfo mail) throws MessagingException;
	
	/*
	 * 		Gửi email đơn giản
	 * 		to email người nhận
	 * 		subject: tiêu đề email
	 * 		body: nội dung email
	 * 		MessagingException lỗi gửi email
	 * 
	 */
	
	public void send(String to, String subject, String body) throws MessagingException;

	public void sendResetPasswordEmail(Account account);
	
	
	
	/*
     * Gửi email chào mừng cho người dùng mới đăng ký
     * to: email người nhận
     * username: tên người dùng mới
     * MessagingException lỗi gửi email
     */

}
