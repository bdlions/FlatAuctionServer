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
import java.util.ArrayList;
import java.util.List;
import org.bdlions.auction.dto.DTOUser;
import org.bdlions.auction.entity.EntityRole;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.EntityUserRole;
import org.bdlions.auction.entity.manager.EntityManagerUser;
import org.bdlions.auction.util.Constants;

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
                dtoUser.getEntityUser().setImg("male.jpg");
                break;
            case Constants.GENDER_ID_FEMALE:
                dtoUser.getEntityUser().setImg("male.jpg");
                break;
            default:
                dtoUser.getEntityUser().setImg("user.jpg");
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
        EntityUser entityUser = entityManagerUser.createUser(dtoUser.getEntityUser(), entityUserRoles);
        if(entityUser != null && entityUser.getId() > 0)
        {
            clientResponse.setSuccess(true);
            return clientResponse;
        }
        return clientResponse;
    }
}
