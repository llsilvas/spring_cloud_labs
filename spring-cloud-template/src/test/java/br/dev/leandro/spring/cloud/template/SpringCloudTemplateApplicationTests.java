package br.dev.leandro.spring.cloud.template;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
class SpringCloudTemplateApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void sample() {
		// @formatter:off
		// tag::example[]
		// Create an ObservationRegistry
		ObservationRegistry registry = ObservationRegistry.create();
		// Register an ObservationHandler
		registry.observationConfig().observationHandler(new MyHandler());

		// Create an Observation and observe your code!
		Observation.createNotStarted("user.name", registry)
				.contextualName("getting-user-name")
				.lowCardinalityKeyValue("userType", "userType1") // let's assume that you can have 3 user types
				.highCardinalityKeyValue("userId", "1234") // let's assume that this is an arbitrary number
				.observe(() -> log.info("Hello")); // this is a shortcut for starting an observation, opening a scope, running user's code, closing the scope and stopping the observation
		// end::example[]
		// @formatter:on
	}

	static class MyHandler implements ObservationHandler<Observation.Context> {

		@Override
		public boolean supportsContext(Observation.Context context) {
			return true;
		}
	}

}
