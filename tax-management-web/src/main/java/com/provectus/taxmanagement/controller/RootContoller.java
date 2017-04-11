package com.provectus.taxmanagement.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by alexey on 19.03.17.
 */
@CrossOrigin
@RestController
public class RootContoller implements ErrorController {

    private static final String PATH = "/error";
    public static final String INDEX_HTML = "index.html";

    @RequestMapping(value = "/")
    public String getIndex() throws IOException {
        URL resource = this.getClass().getClassLoader().getResource(INDEX_HTML);
        return new String(Files.readAllBytes(Paths.get(resource.getFile())));
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
