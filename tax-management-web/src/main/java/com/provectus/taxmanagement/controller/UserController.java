package com.provectus.taxmanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alexey on 26.03.17.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/createSession", method = RequestMethod.GET)
    public String createSession(@PathVariable String login, @PathVariable String password) {
        return "valid_session_token";
    }

}
