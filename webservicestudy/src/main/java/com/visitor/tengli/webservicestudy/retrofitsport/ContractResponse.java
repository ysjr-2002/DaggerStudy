package com.visitor.tengli.webservicestudy.retrofitsport;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "string",strict = false)
public class ContractResponse {
//    @Element(name = "string")
//    public String string;

    @Text
    public String Text;
}
