package org.bdlions.request.handler;

import com.bdlions.dto.Credential;
import org.bdlions.transport.packet.IPacket;
import org.bdlions.session.ISession;
import org.bdlions.session.ISessionManager;
import com.bdlions.util.ACTION;
import com.bdlions.commons.ClientMessages;
import com.bdlions.dto.response.ClientResponse;
import com.bdlions.dto.response.SignInResponse;
import org.bdlions.util.StringUtils;
import org.bdlions.util.annotation.ClientRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bdlions.auction.dto.DTOUser;
import org.bdlions.auction.entity.EntityRole;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.EntityUserRole;
import org.bdlions.auction.entity.manager.EntityManagerUser;
import org.bdlions.auction.util.Constants;
import org.bdlions.library.SendMail;

//import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author nazmul hasan
 */
public class AuthHandler {

    private final ISessionManager sessionManager;

    public AuthHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ClientRequest(action = ACTION.SIGN_IN)
    public ClientResponse signIn(ISession session, IPacket packet) throws Exception 
    {

        SignInResponse response = new SignInResponse();
        if(session != null){
            response.setMessage(ClientMessages.ALREADY_LOGGED_IN);
            response.setSessionId(session.getSessionId());
            response.setUserName(session.getUserName());
            response.setSuccess(true);
            return response;
        }
        if (StringUtils.isNullOrEmpty(packet.getPacketBody())) {
            response.setMessage(ClientMessages.INVALID_SIGNIN_REQUEST_FORMAT);
            response.setSuccess(false);
            return response;
        }

        Gson gson = new Gson();
        Credential credential = gson.fromJson(packet.getPacketBody(), Credential.class);

        if (StringUtils.isNullOrEmpty(credential.getUserName())) {
            response.setMessage(ClientMessages.USER_NAME_IS_MANDATORY);
            response.setSuccess(false);
            return response;
        }
        if (StringUtils.isNullOrEmpty(credential.getPassword())) {
            response.setMessage(ClientMessages.PASSWORD_IS_MANDATORY);
            response.setSuccess(false);
            return response;
        }

//        try{
//            session = sessionManager.createSession(credential);
//        }catch(UnknownAccountException uae){
//            response.setMessage(ClientMessages.INVALID_CREDENTIAL);
//            response.setSuccess(false);
//            return response;
//        }
        try{
            session = sessionManager.createSession(credential);  
            int userId = (int)session.getUserId();
            EntityManagerUser entityManagerUser = new EntityManagerUser();
            EntityUser entityUser = entityManagerUser.getUserByUserId(userId);
            if(entityUser != null && entityUser.getAccountStatusId() != Constants.ACCOUNT_STATUS_ID_ACTIVE)
            {
                response.setMessage("Your account is not active. Please activate your account first.");
                response.setSuccess(false);
                return response;
            }
        }catch(Exception ex){
            response.setMessage(ClientMessages.INVALID_CREDENTIAL);
            response.setSuccess(false);
            return response;
        }
        
        
        if(session == null){
            response.setMessage(ClientMessages.INVALID_CREDENTIAL);
            response.setSuccess(false);
            return response;
        }
        
        session.setRemotePort(packet.getRemotePort());
        session.setRemoteIP(packet.getRemoteIP());
        response.setSessionId((String) session.getSessionId());
        response.setUserName(credential.getUserName());
        response.setFullName(credential.getFirstName() + " " + credential.getLastName());
        response.setSuccess(true);
        

        return response;
    }
    
    @ClientRequest(action = ACTION.SIGN_IN_FB_CODE)
    public ClientResponse signInFBCode(ISession session, IPacket packet) throws Exception 
    {
        SignInResponse response = new SignInResponse();
        if(session != null){
            response.setMessage(ClientMessages.ALREADY_LOGGED_IN);
            response.setSessionId(session.getSessionId());
            response.setUserName(session.getUserName());
            response.setSuccess(true);
            return response;
        }
        if (StringUtils.isNullOrEmpty(packet.getPacketBody())) {
            response.setMessage(ClientMessages.INVALID_SIGNIN_REQUEST_FORMAT);
            response.setSuccess(false);
            return response;
        }

        JsonObject jsonObject = new Gson().fromJson(packet.getPacketBody(), JsonObject.class);     
        String fbCode = jsonObject.get("fbCode").getAsString();

        Credential credential = new Credential();
        try{            
            EntityManagerUser entityManagerUser = new EntityManagerUser();
            EntityUser entityUser = entityManagerUser.getUserByFBCode(fbCode);
            if(entityUser == null || entityUser.getId() == 0)
            {
                response.setMessage("Invalid code. Please try again later.");
                response.setSuccess(false);
                return response;
            }
            credential.setUserName(entityUser.getEmail());
            credential.setPassword(entityUser.getPassword());
            credential.setFirstName(entityUser.getFirstName());
            credential.setLastName(entityUser.getLastName());
            session = sessionManager.createSession(credential);            
        }
        catch(Exception ex)
        {
            response.setMessage("Unable to initialize session. Please try again later.");
            response.setSuccess(false);
            return response;
        }              
        if(session == null){
            response.setMessage("Unable to initialize session. Please try again later.");
            response.setSuccess(false);
            return response;
        }
        
        session.setRemotePort(packet.getRemotePort());
        session.setRemoteIP(packet.getRemoteIP());
        response.setSessionId((String) session.getSessionId());
        response.setUserName(credential.getUserName());
        response.setFullName(credential.getFirstName() + " " + credential.getLastName());
        response.setSuccess(true);
        return response;
    }
    
    @ClientRequest(action = ACTION.SIGN_OUT)
    public ClientResponse signOut(ISession session, IPacket packet) throws Exception 
    {
        if(session != null)
        {
            String sessionId = session.getSessionId();
            try
            {
                sessionManager.destroySession(sessionId);
            }
            catch(Exception ex)
            {
                //add exception in log file
            }
        }
        SignInResponse response = new SignInResponse();
        response.setMessage("Sign out successful");
        response.setSuccess(true);
        return response;
    }
    
    @ClientRequest(action = ACTION.SIGN_UP)
    public ClientResponse registerMember(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        DTOUser dtoUser = gson.fromJson(packet.getPacketBody(), DTOUser.class);
        if(dtoUser == null || dtoUser.getEntityUser() == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid user info. Please try again later.");
            return clientResponse;
        }
        if(StringUtils.isNullOrEmpty(dtoUser.getEntityUser().getFirstName()))
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("First name is required.");
            return clientResponse;
        }
        if(StringUtils.isNullOrEmpty(dtoUser.getEntityUser().getEmail()))
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Email is required.");
            return clientResponse;
        }
        if(StringUtils.isNullOrEmpty(dtoUser.getEntityUser().getPassword()))
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Password is required.");
            return clientResponse;
        }
        if(dtoUser.getRoles() == null || dtoUser.getRoles().isEmpty())
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Role is required.");
            return clientResponse;
        }
        if(dtoUser.getEntityUser().getGenderId() <= 0)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Gender is required.");
            return clientResponse;
        }        
        List<EntityUserRole> entityUserRoles = new ArrayList<>();
        if(dtoUser.getRoles() != null && !dtoUser.getRoles().isEmpty())
        {
            for(EntityRole entityRole: dtoUser.getRoles())
            {
                EntityUserRole entityUserRole = new EntityUserRole();
                entityUserRole.setRoleId(entityRole.getId());
                entityUserRoles.add(entityUserRole);
            }
        }
        //setting a default image to user profile based on gender.
        switch (dtoUser.getEntityUser().getGenderId()) {
            case Constants.GENDER_ID_MALE:
                dtoUser.getEntityUser().setImg(Constants.PROFILE_PICTURE_DEFAULT_MALE);
                break;
            case Constants.GENDER_ID_FEMALE:
                dtoUser.getEntityUser().setImg(Constants.PROFILE_PICTURE_DEFAULT_FEMALE);
                break;
            default:
                dtoUser.getEntityUser().setImg(Constants.PROFILE_PICTURE_DEFAULT);
                break;
        }
        
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        //check whether email already exists or not.
        EntityUser tempEntityUser = entityManagerUser.getUserByEmail(dtoUser.getEntityUser().getEmail());
        if(tempEntityUser != null && tempEntityUser.getId() > 0)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Email already exists or invalid.");
            return clientResponse;
        }
        String emailVerificationCode = UUID.randomUUID().toString();
        dtoUser.getEntityUser().setEmailVerificationCode(emailVerificationCode);
        dtoUser.getEntityUser().setAccountStatusId(Constants.ACCOUNT_STATUS_ID_INACTIVE);
        EntityUser entityUser = entityManagerUser.createUser(dtoUser.getEntityUser(), entityUserRoles);
        if(entityUser != null && entityUser.getId() > 0)
        {            
            SendMail sendMail = new SendMail();
            sendMail.sendSignUpMail(entityUser.getEmail(), emailVerificationCode);
            clientResponse.setSuccess(true);
            clientResponse.setMessage("Account created successfully. Please check your inbox to verify email.");
            return clientResponse;
        }
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FORGET_PASSWORD)
    public ClientResponse forgetPassword(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntityUser reqEntityUser = gson.fromJson(packet.getPacketBody(), EntityUser.class);
        if(reqEntityUser == null || StringUtils.isNullOrEmpty(reqEntityUser.getEmail()))
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid request. Please assign email correctly.");
            return clientResponse;
        }
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        EntityUser entityUser = entityManagerUser.getUserByEmail(reqEntityUser.getEmail());
        if(entityUser != null && entityUser.getId() > 0)
        {
            if(entityUser.getAccountStatusId() != Constants.ACCOUNT_STATUS_ID_ACTIVE)
            {
                clientResponse.setSuccess(false);
                clientResponse.setMessage("Your account is not active. Please verify your email first.");
                return clientResponse;
            }
            SendMail sendMail = new SendMail();
            sendMail.sendForgetPasswordMail(entityUser.getEmail(), entityUser.getPassword());
            clientResponse.setSuccess(true);
            clientResponse.setMessage("Password is sent to your email.");
        }
        else
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid email or email doesn't exist.");
        }
        return clientResponse;
    }
}
