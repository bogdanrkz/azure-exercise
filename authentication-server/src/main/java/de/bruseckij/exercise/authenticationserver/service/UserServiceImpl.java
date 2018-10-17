package de.bruseckij.exercise.authenticationserver.service;

import de.bruseckij.exercise.authenticationserver.entity.User;
import de.bruseckij.exercise.authenticationserver.feign.client.PersonRegistryClient;
import de.bruseckij.exercise.authenticationserver.feign.data.Person;
import de.bruseckij.exercise.authenticationserver.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private PersonRegistryClient personRegistryClient;

	@Autowired
	private UserRepository userRepository;

	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	@Async
	public void updateUser(String userName) {
		logger.info("Update User called for username:{}", userName);
		final Person person = personRegistryClient.findByUserName(userName);
		if (person != null) {
			User user = userRepository.findByUserName(userName).orElse(new User(userName));
			updateUseEntity(person, user);
		}
	}

	private void updateUseEntity(Person person, User user) {
		BeanUtils.copyProperties(person, user, "userName");
		userRepository.save(user);
	}
}
