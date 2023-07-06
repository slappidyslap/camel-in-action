package kg.musabaev;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rabbitmq.RabbitMQComponent;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class Starter {
	public static void main(String[] args) throws Exception {
		CamelContext camel = new DefaultCamelContext();

		var rabbitMq = new RabbitMQComponent();
		rabbitMq.setHostname("localhost");
		rabbitMq.setPortNumber(5672);
		rabbitMq.setUsername("1");
		rabbitMq.setPassword("1");
		camel.addComponent("rabbitmq", rabbitMq);

		camel.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("timer:demo")
						.setBody(simple("Hello from Camel!"))
						.log("${body}")
						.to("rabbitmq:d.test?queue=q.test&declare=true");
			}
		});
		camel.start();
		TimeUnit.SECONDS.sleep(3);
		camel.stop();
	}
}