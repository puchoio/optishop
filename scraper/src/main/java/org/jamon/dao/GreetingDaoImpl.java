package org.jamon.dao;

import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jamon.entity.Greeting;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;

@ApplicationScoped
public class GreetingDaoImpl implements GreetingDao {

	@Inject
	PgPool client;

	@Override
	public Multi<Greeting> getGreetings() {
		return client.query("SELECT * FROM test;").onItem()
				.produceMulti(
						rowSet -> Multi.createFrom().items(() -> StreamSupport.stream(rowSet.spliterator(), false)))
				.onItem().apply(this::map);
	}

	private Greeting map(Row row) {
		final Greeting greeting = new Greeting();
		greeting.id = row.getLong("id");
		greeting.name = row.getString("name");
		greeting.greeting = row.getString("greeting");
		return greeting;
	}
}