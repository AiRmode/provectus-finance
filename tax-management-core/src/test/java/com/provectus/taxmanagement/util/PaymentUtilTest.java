package com.provectus.taxmanagement.util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by agricenko on 10.09.2017.
 */
public class PaymentUtilTest {

    URL resource = Thread.currentThread().getContextClassLoader().getResource("statements.xls");

    @Test
    public void testParseDocument() throws IOException {
        PaymentUtil paymentUtil = new PaymentUtil();
        paymentUtil.parseDocument(new File(resource.getPath()));
    }
}
