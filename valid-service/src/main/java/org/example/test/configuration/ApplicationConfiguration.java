package org.example.test.configuration;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.validation.BeanValidationFeature;
import org.example.test.services.TestWSImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-servlet.xml" })
@ComponentScan({ "org.example.test" })
public class ApplicationConfiguration {

    @Autowired
    private Bus cxfBus;

    @Bean
    public Endpoint testWSEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(cxfBus, new TestWSImpl());
        endpoint.setAddress("/testws");
        endpoint.publish();
        return endpoint;
    }

    @Bean
    public ServletRegistrationBean cxfServlet() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new CXFServlet(), "/services/*");
        servlet.setLoadOnStartup(1);
        return servlet;
    }

    @Bean
    public Feature validationFeature() {
        Feature validationFeature = new BeanValidationFeature();
        validationFeature.initialize(cxfBus);
        cxfBus.getFeatures().add(validationFeature);
        ConstraintViolationInterceptor interceptor = new ConstraintViolationInterceptor();
        cxfBus.getInFaultInterceptors().add(interceptor);
        cxfBus.getOutFaultInterceptors().add(interceptor);
        cxfBus.getProperties().put("exceptionMessageCauseEnabled", true);
        return validationFeature;
    }
}