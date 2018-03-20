package org.bdlions.auction.controller;

/**
 *
 * @author Nazmul Hasan
 */
import java.util.UUID;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.manager.EntityManagerUser;
import org.bdlions.auction.util.Constants;
import org.bdlions.auction.util.ServerConfig;
import org.bdlions.auction.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@RestController
@RequestMapping("/code")
public class ControllerCode {
    private final Logger logger = LoggerFactory.getLogger(ControllerCode.class);
    @RequestMapping("/")
    String home() {
        return "Initial Java Spring service code verification service.";
    }

    @RequestMapping("/signup")
    public ModelAndView successHandler(@RequestParam("vCode") String vCode) 
    {
        if(!StringUtils.isNullOrEmpty(vCode))
        {
            EntityManagerUser entityManagerUser = new EntityManagerUser();
            EntityUser entityUser = entityManagerUser.getUserByEmailVerificationCode(vCode);
            if(entityUser != null && entityUser.getId() > 0)
            {
                entityUser.setEmailVerificationCode("");
                entityUser.setAccountStatusId(Constants.ACCOUNT_STATUS_ID_ACTIVE);
                entityManagerUser.updateUser(entityUser);
                //return "Congratulation! You have activated your account. Please login to your account.";
                return new ModelAndView("redirect:" + ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_URL)+"#/login;id=1");
            }
            else
            {
                return new ModelAndView("redirect:" + ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_URL)+"#/login;id=2");
            }
        }
        return new ModelAndView("redirect:" + ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_URL)+"#/login;id=2");
    }
    
    @RequestMapping("/cancel")
    public String cancelHandler(@RequestParam("cCode") String cCode) 
    {        
        if(!StringUtils.isNullOrEmpty(cCode))
        {
            EntityManagerUser entityManagerUser = new EntityManagerUser();
            EntityUser entityUser = entityManagerUser.getUserByEmailVerificationCode(cCode);
            if(entityUser != null && entityUser.getId() > 0)
            {
                entityUser.setEmailVerificationCode("");
                entityUser.setAccountStatusId(Constants.ACCOUNT_STATUS_ID_INACTIVE);
                String dummyEmail = UUID.randomUUID().toString();
                entityUser.setEmail(dummyEmail);
                entityManagerUser.updateUser(entityUser);
                return "You have cancelled your account. Please signup your new account.";
            }
        }
        return "Invaiid code to cancel your account.";
    }

}
