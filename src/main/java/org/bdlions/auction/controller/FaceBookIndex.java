package org.bdlions.auction.controller;

import com.bdlions.commons.ClientMessages;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.manager.EntityManagerUser;
import org.bdlions.auction.util.Constants;
import org.bdlions.auction.util.FacebookConfig;
import org.bdlions.auction.util.ServerConfig;
import org.bdlions.auction.util.URIBuilder;
import org.bdlions.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


/**
 *
 * @author nazmul hasan
 */
//@CrossOrigin
//@Controller
@RestController
//@RequestMapping(method = RequestMethod.GET)
@RequestMapping("/facebook")
public class FaceBookIndex {

    @RequestMapping("/auth")
    public RedirectView auth() {
        RedirectView redirectView = new RedirectView();
        URIBuilder uriBuilder = new URIBuilder(FacebookConfig.getInstance().get(FacebookConfig.AUTH_URL));
        uriBuilder.addParameter("client_id", FacebookConfig.getInstance().get(FacebookConfig.APP_ID));
        uriBuilder.addParameter("redirect_uri", FacebookConfig.getInstance().get(FacebookConfig.CALLBACK_URL));
        uriBuilder.addParameter("scope", FacebookConfig.getInstance().get(FacebookConfig.SCOPE));
        String url = uriBuilder.toString();
        redirectView.setUrl(url);
        return redirectView;
    }
    
    @RequestMapping("/success")
    public ModelAndView success(@RequestParam("code") String code) {
        if(StringUtils.isNullOrEmpty(code))
        {
            return new ModelAndView("redirect:" + ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_URL)+"#/login;id="+Constants.SIGN_IN_PAGE_ID_FACEBOOK_AUTH_INVLID_CODE);
        }   
        int id = 0;
        //String email = "";
        //String pwd = "";
        String userId = "";
        
        String clientId = FacebookConfig.getInstance().get(FacebookConfig.APP_ID);
        String redirectURI = FacebookConfig.getInstance().get(FacebookConfig.CALLBACK_URL);
        String clientSecret = FacebookConfig.getInstance().get(FacebookConfig.APP_SECRET);

        FacebookClient facebookClient = new DefaultFacebookClient(Version.VERSION_2_8);
        AccessToken accessToken = facebookClient.obtainUserAccessToken(clientId, clientSecret, redirectURI, code);
        if (accessToken != null) {
            if (StringUtils.isNullOrEmpty(accessToken.getAccessToken())) {
                //unauthorized access
                return new ModelAndView("redirect:" + ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_URL)+"#/login;id="+Constants.SIGN_IN_PAGE_ID_FACEBOOK_AUTH_INVLID_TOKEN);
            } 
            else 
            {
                facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), clientSecret, Version.VERSION_2_8);
                User user = facebookClient.fetchObject("me", User.class, Parameter.with("fields", FacebookConfig.getInstance().get(FacebookConfig.SCOPE_REQUIRED)));
                userId = user.getId();
                
                EntityManagerUser entityManagerUser = new EntityManagerUser();
                EntityUser entityUser = entityManagerUser.getUserByFBCode(userId);
                if(entityUser == null)
                {
                    if(!StringUtils.isNullOrEmpty(user.getEmail()))
                    {
                        EntityUser tempEntityUser = entityManagerUser.getUserByEmail(user.getEmail());
                        //register a new user via facebook authentication
                        if(tempEntityUser == null)
                        {
                            EntityUser signUpEntityUser = new EntityUser();
                            signUpEntityUser.setEmail(user.getEmail());
                            signUpEntityUser.setPassword(StringUtils.getRandomString());
                            signUpEntityUser.setFbCode(userId);
                            signUpEntityUser.setAccountStatusId(Constants.ACCOUNT_STATUS_ID_ACTIVE);
                            if(!StringUtils.isNullOrEmpty(user.getFirstName()))
                            {
                                signUpEntityUser.setFirstName(user.getFirstName());
                            }
                            if(!StringUtils.isNullOrEmpty(user.getLastName()))
                            {
                                signUpEntityUser.setLastName(user.getLastName());
                            }                    
                            signUpEntityUser.setImg(Constants.PROFILE_PICTURE_DEFAULT);
                            EntityUser resultEntityUser = entityManagerUser.createUser(signUpEntityUser);
                            if(resultEntityUser != null && resultEntityUser.getId() > 0)
                            {
                                //email = signUpEntityUser.getEmail();
                                //pwd = signUpEntityUser.getPassword();
                                id = Constants.SIGN_IN_PAGE_ID_FACEBOOK_AUTH_SUCCESS;
                            }
                            else
                            {
                                //show proper error message to the user
                                id = Constants.SIGN_IN_PAGE_ID_FACEBOOK_AUTH_FAILE_UNABLE_SIGNUP;                        
                            }
                        }
                        else
                        {
                            //email = tempEntityUser.getEmail();
                            //pwd = tempEntityUser.getPassword();
                            //facebook email already exists in our database so store fb code into the database
                            tempEntityUser.setFbCode(userId);
                            entityManagerUser.updateUser(tempEntityUser);
                            id = Constants.SIGN_IN_PAGE_ID_FACEBOOK_AUTH_SUCCESS;
                        }
                    }
                    else
                    {
                        //facebook didn't retun email, show proper error to client end
                        id = Constants.SIGN_IN_PAGE_ID_FACEBOOK_AUTH_FAILED_EMAIL_INVALID;
                    }
                }
                //facebook code exists into our database
                else
                {
                    id = Constants.SIGN_IN_PAGE_ID_FACEBOOK_AUTH_SUCCESS;
                    //email = entityUser.getEmail();
                    //pwd = entityUser.getPassword();
                }
                if(id != Constants.SIGN_IN_PAGE_ID_FACEBOOK_AUTH_SUCCESS)
                {
                    return new ModelAndView("redirect:" + ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_URL)+"#/login;id=" + id);
                }                
            }
        }
        else
        {
            return new ModelAndView("redirect:" + ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_URL)+"#/login;id="+Constants.SIGN_IN_PAGE_ID_FACEBOOK_AUTH_INVLID_TOKEN);
        }
        return new ModelAndView("redirect:" + ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_URL)+"#/login;id=" + id + ";code=" + userId);
    }
    
    
}
