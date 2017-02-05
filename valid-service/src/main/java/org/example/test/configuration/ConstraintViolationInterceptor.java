package org.example.test.configuration;

import java.text.MessageFormat;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.Soap11FaultOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

public class ConstraintViolationInterceptor extends AbstractSoapInterceptor {

    public ConstraintViolationInterceptor() {
        super(Phase.MARSHAL);
        getBefore().add(Soap11FaultOutInterceptor.class.getName());
    }

    private static final String TEMPLATE = "[{0}] {1} : {2}";

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        Fault fault = (Fault) message.getContent(Exception.class);
        Throwable exception = fault.getCause();
        if (exception instanceof ConstraintViolationException) {
            fault.setMessage(processConstraints((ConstraintViolationException) exception));
        }
    }

    private String processConstraints(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream().map((error) -> {
            return MessageFormat.format(TEMPLATE, error.getPropertyPath(), error.getMessage(), error.getInvalidValue());
        }).collect(Collectors.joining(System.lineSeparator()));
    }

}