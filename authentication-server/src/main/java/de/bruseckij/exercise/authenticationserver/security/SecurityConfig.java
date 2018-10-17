package de.bruseckij.exercise.authenticationserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

	@Autowired
	CustomAuthenticationSuccessHandler authenticationSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").deleteCookies("JSESSIONID").invalidateHttpSession(true)
				.and().authorizeRequests()
				.antMatchers("/oauth-login").permitAll()
				.anyRequest().authenticated()
				.and().exceptionHandling().accessDeniedPage("/access-denied")
				.and().oauth2Login().loginPage("/oauth-login")
				.successHandler(authenticationSuccessHandler)
				.userInfoEndpoint()
				.oidcUserService(oidcUserService);
	}
}
