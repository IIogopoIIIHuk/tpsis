//package com.example.Coursework.Config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT username, password, 1 FROM users WHERE username=?")
//                .authoritiesByUsernameQuery("SELECT username, 'ROLE_USER' FROM users WHERE username=?")
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/login", "/register").permitAll() // Разрешаем доступ к этим URL всем
//                .anyRequest().authenticated() // Все остальные URL требуют аутентификации
//                .and()
//                .formLogin()
//                .loginPage("/login") // Указываем страницу с формой логина
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//}
