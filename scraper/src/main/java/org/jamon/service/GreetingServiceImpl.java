package org.jamon.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jamon.dao.GreetingDao;
import org.jamon.entity.Greeting;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class GreetingServiceImpl implements GreetingService {

	@Inject
	GreetingDao greetingDao;

	@Override
	public Uni<String> greeting(String name) {
		return Uni.createFrom().item(name).onItem().apply(n -> String.format("hello %s", name));
	}

	@Override
	public Multi<Greeting> getGreetings() {
		return greetingDao.getGreetings();
	}
}