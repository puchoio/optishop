package org.jamon.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jamon.entity.Greeting;
import org.jamon.service.GreetingService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/hello")
public class GreetingRest {
	private int count = 0;

	@Inject
	GreetingService service;

	@ConfigProperty(name = "manolo")
	String manolo;

	@ConfigProperty(name = "HOSTNAME")
	String instanceName;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/greeting/{name}")
	public Uni<String> greeting(@PathParam String name) {
		return service.greeting(name + manolo);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/greetings")
	public Multi<Greeting> greetings() {
		System.out.println("Request #" + count++);
		return service.getGreetings();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "adios:  " + instanceName;
	}
}
