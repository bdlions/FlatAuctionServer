package org.bdlions.request.handler;

import org.bdlions.transport.packet.IPacket;
import org.bdlions.session.ISession;
import org.bdlions.session.ISessionManager;
import com.bdlions.util.ACTION;
import com.bdlions.dto.response.ClientListResponse;
import com.bdlions.dto.response.ClientResponse;
import org.bdlions.util.annotation.ClientRequest;
import com.google.gson.Gson;
import java.util.List;
import org.bdlions.auction.dto.DTOProduct;
import org.bdlions.auction.dto.DTOUser;
import org.bdlions.auction.entity.EntityProduct;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.manager.EntityManagerProduct;
import org.bdlions.auction.entity.manager.EntityManagerUser;

//import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author nazmul hasan
 */
public class AdminUserHandler {

    private final ISessionManager sessionManager;

    public AdminUserHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ClientRequest(action = ACTION.FETCH_USER_LIST)
    public ClientResponse getUserList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        Gson gson = new Gson();
        DTOUser dtoUser = gson.fromJson(packet.getPacketBody(), DTOUser.class);
        if(dtoUser == null)
        {
            clientListResponse.setSuccess(false);
            clientListResponse.setMessage("Invalid request to get user list. Please try again later.");
            return clientListResponse;
        }
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        List<EntityUser> entityUserList = entityManagerUser.getUsers(dtoUser.getOffset(), dtoUser.getLimit());
        int totalUser = entityManagerUser.getTotalUsers();
        clientListResponse.setList(entityUserList);
        clientListResponse.setCounter(totalUser);
        return clientListResponse;
    }
}
