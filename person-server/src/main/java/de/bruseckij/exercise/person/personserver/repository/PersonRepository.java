package de.bruseckij.exercise.person.personserver.repository;


import de.bruseckij.exercise.person.personserver.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	Person findByUserName(String userName);
}
