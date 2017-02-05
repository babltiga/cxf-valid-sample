package org.example.test;

import java.io.Writer;

import org.apache.cxf.tools.common.ToolException;
import org.apache.cxf.tools.wsdlto.frontend.jaxws.generators.SEIGenerator;

public class CustomSEIGenerator extends SEIGenerator {

    @Override
    protected void doWrite(String templateName, Writer outputs) throws ToolException {

        if (templateName.endsWith("/sei.vm")) {
            templateName = "valid-sei.vm";
        }

        super.doWrite(templateName, outputs);
    }
}
