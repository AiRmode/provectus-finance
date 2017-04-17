package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * Created by alexey on 28.03.17.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/exchangeRatesUah")
public class ExchangeRatesController {

    @Autowired
    @Qualifier("exchangeRateUahService")
    private ExchangeRateService exchangeRateService;

    @RequestMapping(value = "{currencyCode}/{stringDate}", method = RequestMethod.GET)
    public Double getRatesByDate(@PathVariable String stringDate, @PathVariable String currencyCode) throws ParseException, MalformedURLException, URISyntaxException {
        return exchangeRateService.getRate(stringDate, currencyCode);
    }

}
