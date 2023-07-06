package kg.musabaev;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class Starter {
	public static void main(String[] args) throws Exception {
		CamelContext camel = new DefaultCamelContext();

		camel.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("timer:demo")
						.setBody(simple("Hello world!"))
						.log("${body}");
			}
		});
		camel.start();
		TimeUnit.SECONDS.sleep(3);
		camel.stop();
	}
}