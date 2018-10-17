package de.bruseckij.exercise.authenticationserver.security;

import de.bruseckij.exercise.authenticationserver.service.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public static final String USER_NAME_KEY = "unique_name";

	private final Log logger = LogFactory.getLog(CustomAuthenticationSuccessHandler.class);

	@Autowired
	private UserService userService;

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		super.onAuthenticationSuccess(request, response, authentication);
		updateUserData((OAuth2User) authentication.getPrincipal());
	}

	private void updateUserData(OAuth2User oAuth2User) {
		try {
			final Object userName = oAuth2User.getAttributes().get(USER_NAME_KEY);
			if (userName instanceof String || !isEmpty(userName)) {
				userService.updateUser(((String) userName).toLowerCase());
			}
		} catch (Exception e) {
			//Ignore Exception because application should work even if user server is not available
			logger.error("Exception during update updateUser call", e);
		}
	}
}
