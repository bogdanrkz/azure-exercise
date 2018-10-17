package de.bruseckij.exercise.person.personserver.controller;


import de.bruseckij.exercise.person.personserver.entity.Person;
import de.bruseckij.exercise.person.personserver.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@GetMapping("person/findByUserName/{userName}")
	public Person findByUserName(@PathVariable("userName") String userName) {
		return personRepository.findByUserName(userName);
	}
}
