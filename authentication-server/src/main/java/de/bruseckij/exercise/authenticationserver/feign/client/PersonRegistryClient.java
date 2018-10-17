package de.bruseckij.exercise.authenticationserver.feign.client;

import de.bruseckij.exercise.authenticationserver.feign.data.Person;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "person", url = "${rest.endpoint.person}")
public interface PersonRegistryClient {

	@RequestMapping("/findByUserName/{userName}")
	Person findByUserName(@PathVariable(value = "userName") String name);
}
