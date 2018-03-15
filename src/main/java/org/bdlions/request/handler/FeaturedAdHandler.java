package org.bdlions.request.handler;

import org.bdlions.transport.packet.IPacket;
import org.bdlions.session.ISession;
import org.bdlions.session.ISessionManager;
import com.bdlions.util.ACTION;
import com.bdlions.dto.response.ClientResponse;
import org.bdlions.util.annotation.ClientRequest;
import com.google.gson.Gson;
import org.bdlions.auction.entity.EntityAccountSettingsFA;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.manager.EntityManagerAccountSettingsFA;

//import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author nazmul hasan
 */
public class FeaturedAdHandler {

    private final ISessionManager sessionManager;

    public FeaturedAdHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ClientRequest(action = ACTION.FETCH_USER_ACCOUNT_SETTINGS_FA)
    public ClientResponse getUserAccountSettingsFA(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntityAccountSettingsFA reqEntityAccountSettingsFA = gson.fromJson(packet.getPacketBody(), EntityAccountSettingsFA.class);
        int userId;
        if(reqEntityAccountSettingsFA == null || reqEntityAccountSettingsFA.getUserId() == 0)
        {
            userId = (int)session.getUserId();            
        }
        else
        {
            userId = reqEntityAccountSettingsFA.getUserId();
        } 
        EntityManagerAccountSettingsFA entityManagerAccountSettingsFA = new EntityManagerAccountSettingsFA();
        EntityAccountSettingsFA entityAccountSettingsFA = entityManagerAccountSettingsFA.getAccountSettingsFAByUserId(userId);
        if(entityAccountSettingsFA == null)
        {
            entityAccountSettingsFA = new EntityAccountSettingsFA();
        }
        clientResponse.setSuccess(true);
        clientResponse.setResult(entityAccountSettingsFA);
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.UPDATE_USER_ACCOUNT_SETTINGS_FA)
    public ClientResponse updateUserAccountSettingsFA(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntityAccountSettingsFA entityAccountSettingsFA = gson.fromJson(packet.getPacketBody(), EntityAccountSettingsFA.class);
        if(entityAccountSettingsFA == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid request to update account setting for featured ad.");
            return clientResponse;
        }
        EntityManagerAccountSettingsFA entityManagerAccountSettingsFA = new EntityManagerAccountSettingsFA();
        EntityAccountSettingsFA tempEntityAccountSettingsFA = entityManagerAccountSettingsFA.getAccountSettingsFAByUserId(entityAccountSettingsFA.getUserId());
        if(tempEntityAccountSettingsFA == null)
        {
            if(entityAccountSettingsFA.getUserId() == 0)
            {
                entityAccountSettingsFA.setUserId((int)session.getUserId());
            }
            //insert account setting for featured ad
            entityManagerAccountSettingsFA.createAccountSettingsFA(entityAccountSettingsFA);            
        }
        else
        {
            if(entityAccountSettingsFA.getUserId() == 0)
            {
                clientResponse.setSuccess(false);
                clientResponse.setMessage("Invalid user to update account setting for featured ad.");
                return clientResponse;
            }
            //update account setting for featured ad 
            entityManagerAccountSettingsFA.updateAccountSettingsFA(entityAccountSettingsFA);
        }
        clientResponse.setSuccess(true);
        clientResponse.setMessage("Account setting for featured ad is saved successfully.");
        clientResponse.setResult(entityAccountSettingsFA);
        return clientResponse;
    }
}
