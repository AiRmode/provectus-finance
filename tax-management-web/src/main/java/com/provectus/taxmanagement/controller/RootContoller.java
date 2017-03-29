package com.provectus.taxmanagement.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alexey on 19.03.17.
 */
@RestController
public class RootContoller implements ErrorController {

    private static final String PATH = "/error";

    @CrossOrigin
    @RequestMapping(value = "/")
    public String getIndex() {
        return "Hello World";
    }

    @CrossOrigin
    @RequestMapping(value = PATH)
    public String error() {
        return "My error handler";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
