package org.jamon.service;

import org.jamon.entity.Greeting;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface GreetingService {
	Uni<String> greeting(String name);

	Multi<Greeting> getGreetings();
}