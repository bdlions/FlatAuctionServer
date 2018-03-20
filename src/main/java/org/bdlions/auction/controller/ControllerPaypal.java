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
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@RestController
@RequestMapping("/paypal")
public class ControllerPaypal {
    private final Logger logger = LoggerFactory.getLogger(ControllerPaypal.class);
    @RequestMapping("/")
    String home() {
        return "Initial Java Spring service paypal call back service.";
    }

    @RequestMapping("/success")
    public String successHandler() 
    {
        return "Paypal success page.";
    }
    
    @RequestMapping("/cancel")
    public String cancelHandler() 
    {
        
        return "You have cancelled the request.";
    }
    
}
