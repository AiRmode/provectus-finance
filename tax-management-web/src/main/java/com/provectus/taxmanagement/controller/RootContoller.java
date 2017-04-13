package com.provectus.taxmanagement.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * Created by alexey on 19.03.17.
 */
@CrossOrigin
@RestController(value = "/")
public class RootContoller implements ErrorController {

    private static final String PATH = "/error";
    public static final String INDEX_HTML = "index.html";

    @RequestMapping(value = "/")
    public String getIndex() throws IOException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(INDEX_HTML);
        StringWriter writer = new StringWriter();
        IOUtils.copy(resourceAsStream, writer, Charset.forName("UTF-8"));
        String theString = writer.toString();
        resourceAsStream.close();
        return theString;
    }

    @RequestMapping(value = PATH)
    public String error() {
        return "My error handler";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
