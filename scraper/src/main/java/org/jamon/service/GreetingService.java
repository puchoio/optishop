package org.jamon.service;

import io.smallrye.mutiny.Uni;

public interface GreetingService {
	Uni<String> greeting(String name);
}