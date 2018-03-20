package org.bdlions.auction.controller;

/**
 *
 * @author alamgir
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/worldpay")
public class ControllerWorldPay {
    private final Logger logger = LoggerFactory.getLogger(ControllerPaypal.class);
    @RequestMapping("/")
    String home() {
        return "Initial Java Spring service worldpay call back service.";
    }

    @RequestMapping("/success")
    public String successHandler() 
    {
        return "Worldpay success page.";
    }
    
    @RequestMapping("/cancel")
    public String cancelHandler() 
    {
        
        return "You have cancelled the request.";
    }

}
