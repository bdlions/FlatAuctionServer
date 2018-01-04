package org.bdlions.request.handler;

import com.bdlions.dto.response.ClientListResponse;
import org.bdlions.transport.packet.IPacket;
import org.bdlions.session.ISession;
import org.bdlions.session.ISessionManager;
import com.bdlions.util.ACTION;
import com.bdlions.dto.response.ClientResponse;
import org.bdlions.util.StringUtils;
import org.bdlions.util.annotation.ClientRequest;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bdlions.auction.dto.DTOMessageBody;
import org.bdlions.auction.dto.DTOMessageHeader;
import org.bdlions.auction.entity.EntityMessageBody;
import org.bdlions.auction.entity.EntityMessageHeader;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.manager.EntityManagerMessageBody;
import org.bdlions.auction.entity.manager.EntityManagerMessageHeader;
import org.bdlions.auction.entity.manager.EntityManagerUser;
import org.bdlions.auction.util.TimeUtils;

//import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author nazmul hasan
 */
public class MessageHandler {

    private final ISessionManager sessionManager;

    public MessageHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ClientRequest(action = ACTION.ADD_PRODUCT_MESSAGE)
    public ClientResponse addProductMessage(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        DTOMessageHeader dtoMessageHeader = gson.fromJson(packet.getPacketBody(), DTOMessageHeader.class);
        if(dtoMessageHeader == null || dtoMessageHeader.getEntityMessageHeader() == null || dtoMessageHeader.getEntityMessageBody() == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid message header info. Please try again later.");
            return clientResponse;
        }
        if(StringUtils.isNullOrEmpty(dtoMessageHeader.getEntityMessageHeader().getSubject()))
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid message subject. Please try again later.");
            return clientResponse;
        }
        
        if(StringUtils.isNullOrEmpty(dtoMessageHeader.getEntityMessageBody().getMessage()))
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid message body. Please try again later.");
            return clientResponse;
        }
        int userId = (int)session.getUserId();
        dtoMessageHeader.getEntityMessageHeader().setSenderUserId(userId);
        dtoMessageHeader.getEntityMessageBody().setUserId(userId);
        if(dtoMessageHeader.getEntityMessageHeader().getSenderUserId() == dtoMessageHeader.getEntityMessageHeader().getReceiverUserId())
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Sorry!! You can't send message to yourself.");
            return clientResponse;
        }
        
        //check whether message header exists with product id and sender user id
        EntityManagerMessageHeader entityManagerMessageHeader = new EntityManagerMessageHeader();
        EntityMessageHeader entityMessageHeader = entityManagerMessageHeader.getProductMessageHeaderByUserId(dtoMessageHeader.getEntityMessageHeader().getSenderUserId(), dtoMessageHeader.getEntityMessageHeader().getProductId());
        if(entityMessageHeader == null)
        {
            //create both message header and message body
            EntityMessageHeader emHeader = entityManagerMessageHeader.createMessageHeader(dtoMessageHeader.getEntityMessageHeader(), dtoMessageHeader.getEntityMessageBody());
            if(emHeader != null && emHeader.getId() > 0)
            {
                clientResponse.setSuccess(true);
                clientResponse.setMessage("Message is sent successfully.");
            }
            else
            {
                clientResponse.setSuccess(false);
                clientResponse.setMessage("Unable to send message. Please try again later.");
            }
        }
        else
        {
            //create message body
            dtoMessageHeader.getEntityMessageBody().setMessageHeaderId(entityMessageHeader.getId());
            EntityManagerMessageBody entityManagerMessageBody = new EntityManagerMessageBody();
            EntityMessageBody emBody = entityManagerMessageBody.createMessageBody(dtoMessageHeader.getEntityMessageBody());
            if(emBody != null && emBody.getId() > 0)
            {
                clientResponse.setSuccess(true);
                clientResponse.setMessage("Message is sent successfully.");
            }
            else
            {
                clientResponse.setSuccess(false);
                clientResponse.setMessage("Unable to send message. Please try again later.");
            }
        }        
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.ADD_MESSAGE_BODY)
    public ClientResponse addMessageBody(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntityMessageBody entityMessageBody = gson.fromJson(packet.getPacketBody(), EntityMessageBody.class);
        if(entityMessageBody == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid message body info. Please try again later.");
            return clientResponse;
        }
        if(entityMessageBody.getMessageHeaderId() <= 0)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid message header info. Please try again later.");
            return clientResponse;
        }
        if(StringUtils.isNullOrEmpty(entityMessageBody.getMessage()))
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid message body. Please try again later.");
            return clientResponse;
        }
        int userId = 0;
        if(entityMessageBody.getUserId() == 0)
        {
            userId = (int)session.getUserId();
        }
        else
        {
            userId = entityMessageBody.getUserId();
        }        
        entityMessageBody.setUserId(userId);
        EntityManagerMessageBody entityManagerMessageBody = new EntityManagerMessageBody();
        EntityMessageBody emBody = entityManagerMessageBody.createMessageBody(entityMessageBody);
        if(emBody != null && emBody.getId() > 0)
        {
            clientResponse.setSuccess(true);
            clientResponse.setMessage("Message is sent successfully.");
        }
        else
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Unable to send message. Please try again later.");
        }
        
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_MESSAGE_SENT_LIST)
    public ClientResponse getSentMessageList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();        
        Gson gson = new Gson();
        DTOMessageHeader dtoMessageHeader = gson.fromJson(packet.getPacketBody(), DTOMessageHeader.class);
        if(dtoMessageHeader == null)
        {
            clientListResponse.setSuccess(false);
            clientListResponse.setMessage("Invalid request to get message sent list.");
            return clientListResponse;
        }
        int userId = 0;
        if(dtoMessageHeader.getSender() == null  || dtoMessageHeader.getSender().getId() == 0)
        {
            userId = (int)session.getUserId();
        }
        else
        {
            userId = dtoMessageHeader.getSender().getId();
        }
        
        EntityManagerMessageHeader entityManagerMessageHeader = new EntityManagerMessageHeader();
        List<EntityMessageHeader> messageHeaderList = entityManagerMessageHeader.getSentMessageList(userId, dtoMessageHeader.getOffset(), dtoMessageHeader.getLimit());
        List<Integer> userIdList = new ArrayList<>();
        for(EntityMessageHeader entityMessageHeader : messageHeaderList)
        {
            if(!userIdList.contains(entityMessageHeader.getReceiverUserId()))
            {
                userIdList.add(entityMessageHeader.getReceiverUserId());
            }
        }
        HashMap<Integer, EntityUser> userIdEntityUserMap = new HashMap<>();
        //get entity user list and set it to dtomessageheader
        if(!userIdList.isEmpty())
        {
            EntityManagerUser entityManagerUser = new EntityManagerUser();
            List<EntityUser> userList = entityManagerUser.getUsersByUserIds(userIdList);
            
            for(EntityUser entityUser : userList)
            {
                userIdEntityUserMap.put(entityUser.getId(), entityUser);
            }
        }
        List<DTOMessageHeader> dtoMessageHeaderList = new ArrayList<>();
        for(EntityMessageHeader entityMessageHeader : messageHeaderList)
        {
            DTOMessageHeader tempDTOMessageHeader = new DTOMessageHeader();
            tempDTOMessageHeader.setReceiver(userIdEntityUserMap.get(entityMessageHeader.getReceiverUserId()));
            tempDTOMessageHeader.setEntityMessageHeader(entityMessageHeader);
            dtoMessageHeaderList.add(tempDTOMessageHeader);
        }
        clientListResponse.setList(dtoMessageHeaderList);
        clientListResponse.setCounter(entityManagerMessageHeader.getTotalSentMessageList(userId));
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_MESSAGE_INBOX_LIST)
    public ClientResponse getInboxMessageList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        
        Gson gson = new Gson();
        DTOMessageHeader dtoMessageHeader = gson.fromJson(packet.getPacketBody(), DTOMessageHeader.class);
        if(dtoMessageHeader == null)
        {
            clientListResponse.setSuccess(false);
            clientListResponse.setMessage("Invalid request to get inbox message list.");
            return clientListResponse;
        }
        int userId = 0;
        if(dtoMessageHeader.getSender() == null || dtoMessageHeader.getSender().getId() == 0)
        {
            userId = (int)session.getUserId();
        }
        else
        {
            userId = dtoMessageHeader.getSender().getId();
        }
        
        EntityManagerMessageHeader entityManagerMessageHeader = new EntityManagerMessageHeader();
        List<EntityMessageHeader> messageHeaderList = entityManagerMessageHeader.getInboxMessageList(userId, dtoMessageHeader.getOffset(), dtoMessageHeader.getLimit());
        List<Integer> userIdList = new ArrayList<>();
        for(EntityMessageHeader entityMessageHeader : messageHeaderList)
        {
            if(!userIdList.contains(entityMessageHeader.getReceiverUserId()))
            {
                userIdList.add(entityMessageHeader.getReceiverUserId());
            }
            if(!userIdList.contains(entityMessageHeader.getSenderUserId()))
            {
                userIdList.add(entityMessageHeader.getSenderUserId());
            }
        }
        HashMap<Integer, EntityUser> userIdEntityUserMap = new HashMap<>();
        //get entity user list and set it to dtomessageheader
        if(!userIdList.isEmpty())
        {
            EntityManagerUser entityManagerUser = new EntityManagerUser();
            List<EntityUser> userList = entityManagerUser.getUsersByUserIds(userIdList);
            
            for(EntityUser entityUser : userList)
            {
                userIdEntityUserMap.put(entityUser.getId(), entityUser);
            }
        }
        List<DTOMessageHeader> dtoMessageHeaderList = new ArrayList<>();
        for(EntityMessageHeader entityMessageHeader : messageHeaderList)
        {
            int receiverId = 0;
            if(userId == entityMessageHeader.getSenderUserId())
            {
                receiverId = entityMessageHeader.getReceiverUserId();
            }
            else
            {
                receiverId = entityMessageHeader.getSenderUserId();
            }
            
            DTOMessageHeader tempDTOMessageHeader = new DTOMessageHeader();
            tempDTOMessageHeader.setReceiver(userIdEntityUserMap.get(receiverId));
            tempDTOMessageHeader.setEntityMessageHeader(entityMessageHeader);
            dtoMessageHeaderList.add(tempDTOMessageHeader);
        }
        clientListResponse.setList(dtoMessageHeaderList);
        clientListResponse.setCounter(entityManagerMessageHeader.getTotalInboxMessageList(userId));
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_MESSAGE_BODY_LIST)
    public ClientResponse getMessageBodyList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientResponse = new ClientListResponse();
        
        Gson gson = new Gson();
        DTOMessageBody dtoMessageBody = gson.fromJson(packet.getPacketBody(), DTOMessageBody.class);
        
        if(dtoMessageBody == null || dtoMessageBody.getEntityMessageBody() == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid request with message body. Please try again later.");
            return clientResponse;
        }
        
        EntityManagerMessageBody entityManagerMessageBody = new EntityManagerMessageBody();
        List<EntityMessageBody> messageBodyList = entityManagerMessageBody.getMessageBodyList(dtoMessageBody.getEntityMessageBody().getMessageHeaderId(), dtoMessageBody.getOffset(), dtoMessageBody.getLimit());
        List<Integer> userIdList = new ArrayList<>();
        for(EntityMessageBody entityMessageBody : messageBodyList)
        {
            if(!userIdList.contains(entityMessageBody.getUserId()))
            {
                userIdList.add(entityMessageBody.getUserId());
            }
        }
        HashMap<Integer, EntityUser> userIdEntityUserMap = new HashMap<>();
        //get entity user list and set it to dtomessageheader
        if(!userIdList.isEmpty())
        {
            EntityManagerUser entityManagerUser = new EntityManagerUser();
            List<EntityUser> userList = entityManagerUser.getUsersByUserIds(userIdList);
            
            for(EntityUser entityUser : userList)
            {
                userIdEntityUserMap.put(entityUser.getId(), entityUser);
            }
        }
        List<DTOMessageBody> dtoMessageBodyList = new ArrayList<>();
        TimeUtils timeUtils = new TimeUtils();
        for(EntityMessageBody entityMessageBody : messageBodyList)
        {
            DTOMessageBody tempDTOMessageBody = new DTOMessageBody();
            tempDTOMessageBody.setEntityUser(userIdEntityUserMap.get(entityMessageBody.getUserId()));
            tempDTOMessageBody.setEntityMessageBody(entityMessageBody);
            tempDTOMessageBody.setCreatedTime(timeUtils.convertUnixToHuman(entityMessageBody.getCreatedOn(), ""));
            dtoMessageBodyList.add(tempDTOMessageBody);
        }
        clientResponse.setList(dtoMessageBodyList);
        clientResponse.setCounter(entityManagerMessageBody.getMessageTotalBodyList(dtoMessageBody.getEntityMessageBody().getMessageHeaderId()));
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_MESSAGE_HEADER)
    public ClientResponse getMessageHeader(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        
        Gson gson = new Gson();
        EntityMessageHeader entityMessageHeader = gson.fromJson(packet.getPacketBody(), EntityMessageHeader.class);
        
        if(entityMessageHeader == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid request to get message header. Please try again later.");
            return clientResponse;
        }
        DTOMessageHeader dtoMessageHeader = new DTOMessageHeader();
        EntityManagerMessageHeader entityManagerMessageHeader = new EntityManagerMessageHeader();
        EntityMessageHeader emHeader = entityManagerMessageHeader.getMessageHeaderById(entityMessageHeader.getId());
        if(emHeader != null && emHeader.getSenderUserId() > 0)
        {
            dtoMessageHeader.setEntityMessageHeader(emHeader);
            EntityManagerUser entityManagerUser = new EntityManagerUser();
            EntityUser entityUser = entityManagerUser.getUserByUserId(emHeader.getSenderUserId());
            dtoMessageHeader.setSender(entityUser);
            clientResponse.setResult(dtoMessageHeader);
            clientResponse.setSuccess(true);
        }
        else
        {
            clientResponse.setSuccess(false);
        }
        return clientResponse;
    }
}
