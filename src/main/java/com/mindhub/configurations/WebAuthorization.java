package com.mindhub.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                .antMatchers(HttpMethod.PATCH, "/api/nightmode").hasAuthority("USER")

                .antMatchers(HttpMethod.PATCH, "/api/cards/deletecard").hasAuthority("USER")

                .antMatchers(HttpMethod.PATCH, "/api/accounts/**").hasAuthority("USER")

                .antMatchers(HttpMethod.POST, "/api/clients/accounts").hasAuthority("USER")

                .antMatchers(HttpMethod.POST, "/api/transactions").hasAuthority("USER")

                .antMatchers(HttpMethod.POST, "/api/loans").hasAuthority("USER")

                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()

                .antMatchers("/rest/**", "/web/manager.html", "/h2-console", "/h2-console/**", "/api/clients", "/api/accounts").hasAuthority("ADMIN")

                .antMatchers("/web/accounts.html", "/web/transactions.html", "/web/cards.html", "web/loans.html", "web/create-cards.html", "web/create-loans.html", "/api/clients/current", "api/accounts").hasAuthority("USER")

                .antMatchers("/web/index.html", "/web/js/**", "/web/css/**", "/web/assets/**", "/web/favicon.ico").permitAll();


        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");



        http.logout().logoutUrl("/api/logout");


        // turn off checking for CSRF tokens

        http.csrf().disable();


        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }


}
