package com.catalog.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/*import com.tabletennis.repositories.UserRepository;
import com.tabletennis.services.MyUserDetailsService;*/

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.catalog.service"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	//private MyUserDetailsService customUserDetailsService;
	
    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;
     
    @Autowired
    DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable().authorizeRequests()
            //.antMatchers("/register**", "/about").permitAll().anyRequest().permitAll()
            .antMatchers("/resources/static/css/**").permitAll()
            .antMatchers("/resources/static/js/**").permitAll()
            .antMatchers("/login**").permitAll()
            .antMatchers("/webjars/**").permitAll();

        httpSecurity.authorizeRequests().anyRequest().permitAll();//.authenticated();
//            .antMatchers("/addNewUser").permitAll()
//            .antMatchers("/userCreateFormHandling").permitAll()
//            .antMatchers("/login**").permitAll()
//            .antMatchers("/logout**").permitAll()
//            .antMatchers("/forgotPassword**").permitAll()
//            .antMatchers("/user/resetPassword**").permitAll()
//            .antMatchers("/resetPassword**").access("hasAuthority('Admin') or hasAuthority('User')")
//            .antMatchers("/sendToken**").permitAll()
//
//
//            .antMatchers("/usernameNotUsed/**").permitAll()
//            .antMatchers("/emailNotUsed/**").permitAll()
//            .antMatchers("/user/activateUser**").permitAll()
//            .antMatchers("/img/**").permitAll()
//            .antMatchers("/user/uploadImage**").permitAll()
//            .antMatchers("/user/getImage/**").permitAll()
//        //httpSecurity.authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().permitAll();
//
//            .antMatchers("/admin/**").hasRole("Admin")
//            .antMatchers("/db/**").access("hasRole('Admin') and hasRole('DBA')")
//            .antMatchers("/user").access("hasRole('User')")
//            .antMatchers("/video/**").access("hasAuthority('Admin') or hasAuthority('User')")
//            .antMatchers("/user/savePassword").access("hasAuthority('Admin') or hasAuthority('User')")
//            .antMatchers("/viewEditProfile").access("hasAuthority('Admin') or hasAuthority('User')")
//
//        httpSecurity//.csrf().disable()
//                .authorizeRequests()
                //.anyRequest().authenticated()
        httpSecurity
                .formLogin().loginPage("/login").defaultSuccessUrl("/titles")
                .and()
                .logout()
                .logoutUrl("/logout").deleteCookies("remember-me").permitAll();
//            .anyRequest().authenticated()
//            .and()
//            .formLogin().loginPage("/login") .defaultSuccessUrl("/index.html")
//            .permitAll()
//            .and()
//            .logout()
//            .logoutUrl("/logout").deleteCookies("remember-me")
//            .and()
//            .rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(31536000);
            /*.tokenValiditySeconds(31536000)*/;
        httpSecurity.authorizeRequests().antMatchers("/resources/**").permitAll();



	}
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /*auth
            .inMemoryAuthentication()
                .withUser("Cukara").password("bog").roles("USER");*/
		
		//auth.authenticationProvider(authProvider);
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
    }
	
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }
}
