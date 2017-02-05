package org.example.test.services;

import javax.jws.WebService;

import org.example.test.NewOperation;
import org.example.test.NewOperationResponse;
import org.example.test.ObjectFactory;
import org.example.test.TestWS;

@WebService(endpointInterface = "org.example.test.TestWS", portName = "TestWSPort", serviceName = "TestWS", targetNamespace = "http://www.example.org/test/")
public class TestWSImpl implements TestWS {

    @Override
    public NewOperationResponse newOperation(NewOperation parameters) {
	int in = parameters.getIn();
	NewOperationResponse response = new ObjectFactory().createNewOperationResponse();
	if (in < 10 || in > 20) {
	    response.setOut("no no no");
	} else {
	    response.setOut("OK");
	}
	return response;
    }

}