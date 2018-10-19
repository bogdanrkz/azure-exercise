package de.bruseckij.exercise.authenticationserver.service;


import de.bruseckij.exercise.authenticationserver.entity.User;
import de.bruseckij.exercise.authenticationserver.feign.client.PersonRegistryClient;
import de.bruseckij.exercise.authenticationserver.feign.data.Person;
import de.bruseckij.exercise.authenticationserver.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplUnitTest {

	@Mock
	private PersonRegistryClient personRegistryClient;

	@Mock
	private UserRepository userRepository;

	@Captor
	private ArgumentCaptor<User> userArgumentCaptor;

	private static final String TEST_USER_NAME = "test@company.com";

	@InjectMocks
	private final UserService userService = new UserServiceImpl();

	@Test
	public void testThatUserRepositoryIsNotCalledIfPersonNotFoundInRegistry() {
		when(personRegistryClient.findByUserName(TEST_USER_NAME)).thenReturn(null);

		userService.updateUser(TEST_USER_NAME);

		verifyZeroInteractions(userRepository);
	}

	@Test
	public void testThatUpdateUserSavesNewUserIfFoundInRegistry() {
		Person person = new Person(TEST_USER_NAME, "firstName", "lastName");

		when(personRegistryClient.findByUserName(TEST_USER_NAME)).thenReturn(person);

		userService.updateUser(TEST_USER_NAME);

		verify(userRepository).save(userArgumentCaptor.capture());

		User capturedUser = userArgumentCaptor.getValue();

		assertThat(capturedUser.getFirstName(), is(person.getFirstName()));
		assertThat(capturedUser.getLastName(), is(person.getLastName()));
		assertThat(capturedUser.getUserName(), is(person.getUserName()));
		assertThat(capturedUser.getId(), is(nullValue()));
	}

	@Test
	public void testThatUpdateUserUpdatesExistingUserIfFoundInRegistry() {
		Person person = new Person(TEST_USER_NAME, "firstName", "lastName");

		when(personRegistryClient.findByUserName(TEST_USER_NAME)).thenReturn(person);

		User user = new User(TEST_USER_NAME, "xxx", "yyy");
		user.setId(22l);

		when(userRepository.findByUserName(TEST_USER_NAME)).thenReturn(Optional.of(user));

		userService.updateUser(TEST_USER_NAME);

		verify(userRepository).findByUserName(TEST_USER_NAME);
		verify(userRepository).save(userArgumentCaptor.capture());

		User capturedUser = userArgumentCaptor.getValue();

		assertThat(capturedUser.getFirstName(), is(person.getFirstName()));
		assertThat(capturedUser.getFirstName(), is(person.getFirstName()));
		assertThat(capturedUser.getLastName(), is(person.getLastName()));
		assertThat(capturedUser.getId(), is(22l));
	}

}