package kg.musabaev;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rabbitmq.RabbitMQComponent;
import org.apache.camel.main.Main;

import java.util.concurrent.TimeUnit;

public class CamelInActionApplication {
	public static void main(String[] args) throws Exception {
		Main camel = new Main();
		var rabbitMq = new RabbitMQComponent();
		rabbitMq.setHostname("localhost");
		rabbitMq.setPortNumber(5672);
		rabbitMq.setUsername("1");
		rabbitMq.setPassword("1");
		camel.bind("rabbitmq", rabbitMq);

		camel.configure().addRoutesBuilder(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("timer:demo")
						.setBody(simple("Hello from Camel!"))
						.log("${body}")
						.to("rabbitmq:d.test?queue=q.test&declare=true");
			}
		});
		new Thread(() -> {
			try {
				camel.run(args);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).start();
		TimeUnit.SECONDS.sleep(3);
		camel.completed();
	}
}