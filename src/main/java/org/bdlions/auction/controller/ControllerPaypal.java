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
@RequestMapping("/paypal")
public class ControllerPaypal {
    private final Logger logger = LoggerFactory.getLogger(ControllerPaypal.class);
    @RequestMapping("/")
    String home() {
        return "Initial Java Spring service paypal call back service.";
    }

    @RequestMapping("/success")
    public void successHandler(@RequestParam("tx") String tx) 
    {
        System.out.println("tx:" + tx);
    }
    
    @RequestMapping("/cancel")
    public String cancelHandler() 
    {
        
        return "You have cancelled the request.";
    }

}
