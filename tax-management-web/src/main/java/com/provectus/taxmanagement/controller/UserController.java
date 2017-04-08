package com.provectus.taxmanagement.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Created by alexey on 26.03.17.
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/createSession", method = RequestMethod.GET)
    public String createSession(@RequestParam String login, @RequestParam String password) {
        return "valid_session_token";
    }

}
