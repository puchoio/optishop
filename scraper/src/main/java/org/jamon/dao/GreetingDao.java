package org.jamon.dao;

import org.jamon.entity.Greeting;

import io.smallrye.mutiny.Multi;

public interface GreetingDao {
	Multi<Greeting> getGreetings();
}