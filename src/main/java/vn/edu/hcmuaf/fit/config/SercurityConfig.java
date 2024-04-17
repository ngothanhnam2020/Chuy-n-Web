package vn.edu.hcmuaf.fit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SercurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		
		//nếu gõ vào "/" thì cho phép đi qua
		.antMatchers("/u/**","/js/**" , "/css/**", "/img/**", "/fonts/**", "/dt/**","/assets/**","/css/icons/material-design-iconic-font//fonts/**", "/vendor/**").permitAll()
		
		//những cái request còn lại thì bắt chứng thực hết (ý là phải đăng nhập mới vào được)
		.anyRequest().authenticated()
		.and()
		.formLogin();
		http.csrf().disable(); //cái này là để gửi post mà không bị lỗi 403
		//nó sẽ chuyển qua trang login của mình thay vì trang login spring security tự tạo
		//vì "/login" cũng là 1 request mà phía trên đã nói là những cái request còn lại thì bắt chứng thực hết => phải có .permitAll() để cho phép mó qua
//		.formLogin().loginPage("/login").permitAll()
//		.defaultSuccessUrl("/login?success=true")
//		.failureUrl("/login?success=fail")
//		.usernameParameter("username")//
//		.passwordParameter("password");
		
	}
	
	//nơi quản lý tất cả các chứng thực
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//tạo ra 1 cái session có username và password
		auth
        .inMemoryAuthentication()
        .withUser("admin")
        .password(passwordEncoder().encode("123"))
//        .authorities("ROLE_ADMIN");
        .roles("ADMIN");
	}
}
