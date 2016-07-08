package com.luxoft.wheretogo.configuration;

import com.luxoft.wheretogo.services.MyUserDetailsService;
import com.luxoft.wheretogo.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by sasha on 06.07.16.
 */
@Configuration
@EnableWebSecurity
public class ConfigureSpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService myUserDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/myEvents", "/sessionUser", "/getEventId",
                        "/addEvent", "/deleteEvent", "/updateEvent", "/myEventsCategories",
                        "/user", "/userInfo", "/assignEventToUser",
                        "/unassignEventFromUser", "/archivedEvents", "/archivedUsersEvents",
                        "/archivedEventsCategories", "/archivedUsersEventsCategories").authenticated()
                .anyRequest().permitAll().and()
                .formLogin().loginPage("/login").failureUrl("/?error_login")
                .usernameParameter("userEmail")
                .passwordParameter("userPassword")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and().csrf();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
