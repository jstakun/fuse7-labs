package org.fuse.usecase;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.cxf.message.MessageContentsList;
import org.globex.Account;
import org.globex.Company;
import org.globex.CorporateAccount;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Aggregator implementation which extract the id and salescontact
 * from CorporateAccount and update the Account
 */
public class AccountAggregator implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

    	if (oldExchange == null) {
            return newExchange;
        } else {
        	
        	//oldExchange is Account
        	//newExchange is CorporateAccount
        	
        	Object no = newExchange.getIn().getBody();
        	
        	if (no instanceof CorporateAccount) {
        	
        		CorporateAccount ca = (CorporateAccount) no;
        		Account a = (Account) oldExchange.getIn().getBody();
        	
        		System.err.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" 
    			+ "newExchange: " + newExchange.getIn().getBody()
    			+ "\naaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n"
    			+ "oldExchange: " + oldExchange.getIn().getBody());
        	
        		a.setSalesRepresentative(ca.getSalesContact());
        	
        		oldExchange.getIn().setBody(a);
        	}
    	
        	return oldExchange;
        }
    }
    
}