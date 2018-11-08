package org.fuse.usecase;

import static org.junit.Assert.*;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.globex.Account;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JacksonCompanyTest extends CamelSpringTestSupport {

	// Expected message bodies
	protected Object[] inputBodies = {"{\"company\":{\"name\":\"Rotobots\",\"geo\":\"NA\",\"active\":true},\"contact\":{\"firstName\":\"Bill\",\"lastName\":\"Smith\",\"streetAddr\":\"100 N Park Ave.\",\"city\":\"Phoenix\",\"state\":\"AZ\",\"zip\":\"85017\",\"phone\":\"602-555-1100\"}}"};
	
	// Templates to send to input endpoints
	@Produce(uri = "direct:test")
	protected ProducerTemplate inputEndpoint;
	// Mock endpoints used to consume messages from the output endpoints and then perform assertions
	@EndpointInject(uri = "mock:test")
	protected MockEndpoint outputEndpoint;

	@Test
	public void testCamelRoute() throws Exception {

		// Define some expectations

		// For now, let's just wait for some messages
		outputEndpoint.expectedMessageCount(1);
		// TODO Add some expectations here
		// Send some messages to input endpoints
		for (Object inputBody : inputBodies) {
			inputEndpoint.sendBody(inputBody);
		}
		
		Exchange exchange = outputEndpoint.getExchanges().get(0);
        Message in = exchange.getIn();
        Account a = (Account) in.getBody();
        
        assertEquals(a.getCompany().getName(), "Rotobots");
		
        // Validate our expectations
		assertMockEndpointsSatisfied();
	}

	@Override
	protected ClassPathXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("spring/camel-context.xml");
	}

}
