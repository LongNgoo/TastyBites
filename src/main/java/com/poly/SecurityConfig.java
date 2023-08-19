package com.poly;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.poly.model.Account;
import com.poly.service.AccountService;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	AccountService accountService;
	
	
	@Autowired
	HttpSession session;
	
	@Autowired
    PasswordEncoder passwordEncoder; // Sử dụng PasswordEncoder chung cho mã hóa mật khẩu
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	// Cung cấp nguồn dữ liệu đăng nhập
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            try {
                Account user = accountService.findById(username);
                String password = user.getPassword(); // Lấy mật khẩu không mã hóa từ đối tượng Account

                String[] roles = user.getAuthorities().stream()
                        .map(er -> er.getRole().getId())
                        .collect(Collectors.toList()).toArray(new String[0]);
                Map<String, Object> authentication = new HashMap<>();
                authentication.put("user", user);
                byte[] token = (username + ":" + password).getBytes();
                authentication.put("token", "Basic " + Base64.getEncoder().encodeToString(token));
                session.setAttribute("authentication", authentication);

                return User.withUsername(username).password(password).roles(roles).build();
            } catch (NoSuchElementException e) {
                throw new UsernameNotFoundException(username + " not found!");
            }
        }).passwordEncoder(passwordEncoder());
    }
	
	// Phân quyền sử dụng
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/order/**").hasAnyRole("STAF", "CUST")                           // với url bắt đầu /order thì yêu cầu phải đăng nhập
		.antMatchers("/favourite/**").authenticated()
		.antMatchers("/cart/**").authenticated()
		.antMatchers("/admin/**").hasAnyRole("STAF", "DIRE")
		.antMatchers("/rest/authorities").hasRole("DIRE")
		.anyRequest().permitAll();
		
		http.formLogin().loginPage("/security/login/form").loginProcessingUrl("/security/login")
		.defaultSuccessUrl("/", true)
		.failureUrl("/security/login/error");
		
		http.rememberMe()
		.tokenValiditySeconds(86400);                                       // 86400 giây, tức là 24 giờ
		
		/*
		 *	sau khi đăng nhập 
		 *	nếu truy cập địa chỉ chưa được cấp quyền thì chuyển sang unauthoried
		 *	localhost:8080/security/unauthoried
		 *
		 * */
		http.exceptionHandling()
		.accessDeniedPage("/security/unauthoried");
		
		http.logout()
		.logoutUrl("/security/logoff")
		.logoutSuccessUrl("/security/logoff/success");
		
		http.oauth2Login()
		.loginPage("/auth/login/form")
		.defaultSuccessUrl("/oauth2/login/success", true)
		.failureUrl("/auth/login/error")
		.authorizationEndpoint()
		.baseUri("/oauth2/authorization");
	}
	

	
	// Cho phép truy xuất REST API từ bên ngoài (domain khác)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
