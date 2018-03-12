package org.bdlions.request.handler;

import org.bdlions.transport.packet.IPacket;
import org.bdlions.session.ISession;
import org.bdlions.session.ISessionManager;
import com.bdlions.util.ACTION;
import com.bdlions.dto.response.ClientListResponse;
import com.bdlions.dto.response.ClientResponse;
import org.bdlions.util.annotation.ClientRequest;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.bdlions.auction.dto.DTOAutoBid;
import org.bdlions.auction.dto.DTOBid;
import org.bdlions.auction.entity.EntityAutoBid;
import org.bdlions.auction.entity.EntityBid;
import org.bdlions.auction.entity.EntityProduct;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.manager.EntityManagerAutoBid;
import org.bdlions.auction.entity.manager.EntityManagerBid;
import org.bdlions.auction.entity.manager.EntityManagerProduct;
import org.bdlions.auction.entity.manager.EntityManagerUser;
import org.bdlions.auction.util.TimeUtils;

//import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author nazmul hasan
 */
public class BidHandler {

    private final ISessionManager sessionManager;

    public BidHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ClientRequest(action = ACTION.ADD_PRODUCT_BID)
    public ClientResponse addBidInfo(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntityBid reqEntityBid = gson.fromJson(packet.getPacketBody(), EntityBid.class);
        if(reqEntityBid == null )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid bid info. Please try again later.");
            return clientResponse;
        }
        if( reqEntityBid.getProductId() <= 0 )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid product to add a bid. Please try again later.");
            return clientResponse;
        }
        if( reqEntityBid.getPrice() <= 0 )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Please assign valid amount for a bid.");
            return clientResponse;
        }
        
        
        EntityManagerProduct entityManagerProduct = new EntityManagerProduct();
        EntityProduct entityProduct = entityManagerProduct.getProductById(reqEntityBid.getProductId());
        TimeUtils timeUtils = new TimeUtils();
        long currentTime = timeUtils.getCurrentTime();
        if(currentTime > entityProduct.getUnixAuctionEnd())
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Sorry!! Auction is ended.");
            return clientResponse;
        }        
        
        int userId = (int)session.getUserId();
        if(userId == entityProduct.getUserId())
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Sorry!! You Can't place a bid on your own ad.");
            return clientResponse;
        }
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        EntityUser entityUser = entityManagerUser.getUserByUserId(userId);        
        reqEntityBid.setUserId(userId);
        reqEntityBid.setFullName(entityUser.getFirstName() + " " +entityUser.getLastName());
        
        EntityManagerBid entityManagerBid = new EntityManagerBid();
        EntityBid entityBid = entityManagerBid.createBid(reqEntityBid);
        if(entityBid != null && entityBid.getId() > 0)
        {
            //---------------------after adding product bid, update bid counter at product info.
            entityProduct.setTotalBids(entityProduct.getTotalBids() + 1);
            entityManagerProduct.updateProduct(entityProduct);
            clientResponse.setSuccess(true);
            clientResponse.setResult(entityBid);
            clientResponse.setMessage("A bid is placed successfully.");
        }
        else
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Unable to add a bid. Please try again later.");
        }
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_PRODUCT_BID_LIST)
    public ClientResponse getProductBidList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientResponse = new ClientListResponse();
        Gson gson = new Gson();
        DTOBid dtoBid = gson.fromJson(packet.getPacketBody(), DTOBid.class);
        
        if(dtoBid == null || dtoBid.getEntityBid() == null )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid request to get bid list. Please try again later.");
            return clientResponse;
        }
        if(dtoBid.getEntityBid().getProductId() <=0 )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid product to get bid list. Please try again later.");
            return clientResponse;
        }
        EntityManagerBid entityManagerBid = new EntityManagerBid();
        List<EntityBid> bids = entityManagerBid.getProductBids(dtoBid.getEntityBid().getProductId(), dtoBid.getOffset(), dtoBid.getLimit());
        int totalBids = entityManagerBid.getProductTotalBids(dtoBid.getEntityBid().getProductId());
        
        List<EntityBid> bidList = new ArrayList<>();
        TimeUtils timeUtils = new TimeUtils();
        for(EntityBid entityBid : bids)
        {
            entityBid.setCreatedTime(timeUtils.convertUnixToHuman(entityBid.getCreatedOn(), ""));
            bidList.add(entityBid);
        }
        
        clientResponse.setList(bidList);
        clientResponse.setCounter(totalBids);
        clientResponse.setSuccess(true);
        return clientResponse;
    }
    
    //Auto Bid Section Starts
    @ClientRequest(action = ACTION.ADD_PRODUCT_AUTO_BID)
    public ClientResponse addAutoBidInfo(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntityAutoBid reqEntityAutoBid = gson.fromJson(packet.getPacketBody(), EntityAutoBid.class);
        if(reqEntityAutoBid == null )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid auto bid info. Please try again later.");
            return clientResponse;
        }
        if( reqEntityAutoBid.getProductId() <= 0 )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid product to add an auto bid. Please try again later.");
            return clientResponse;
        }
        if( reqEntityAutoBid.getPrice() <= 0 )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Please assign valid amount for an auto bid.");
            return clientResponse;
        }
        
        
        EntityManagerProduct entityManagerProduct = new EntityManagerProduct();
        EntityProduct entityProduct = entityManagerProduct.getProductById(reqEntityAutoBid.getProductId());
        TimeUtils timeUtils = new TimeUtils();
        long currentTime = timeUtils.getCurrentTime();
        if(currentTime > entityProduct.getUnixAuctionEnd())
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Sorry!! Auction is ended.");
            return clientResponse;
        }        
        
        int userId = (int)session.getUserId();
        if(userId == entityProduct.getUserId())
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Sorry!! You Can't place an auto bid on your own ad.");
            return clientResponse;
        }
        EntityManagerUser entityManagerUser = new EntityManagerUser();
        EntityUser entityUser = entityManagerUser.getUserByUserId(userId);        
        reqEntityAutoBid.setUserId(userId);
        reqEntityAutoBid.setFullName(entityUser.getFirstName() + " " +entityUser.getLastName());
        
        EntityManagerAutoBid entityManagerAutoBid = new EntityManagerAutoBid();
        EntityAutoBid entityAutoBid = entityManagerAutoBid.createAutoBid(reqEntityAutoBid);
        if(entityAutoBid != null && entityAutoBid.getId() > 0)
        {
            clientResponse.setSuccess(true);
            clientResponse.setResult(entityAutoBid);
            clientResponse.setMessage("An auto bid is placed successfully.");
        }
        else
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Unable to add an auto bid. Please try again later.");
        }
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_PRODUCT_AUTO_BID_LIST)
    public ClientResponse getProductAutoBidList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientResponse = new ClientListResponse();
        Gson gson = new Gson();
        DTOAutoBid dtoAutobid = gson.fromJson(packet.getPacketBody(), DTOAutoBid.class);
        
        if(dtoAutobid == null || dtoAutobid.getEntityAutoBid() == null )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid request to get auto bid list. Please try again later.");
            return clientResponse;
        }
        if(dtoAutobid.getEntityAutoBid().getProductId() <=0 )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid product to get auto bid list. Please try again later.");
            return clientResponse;
        }
        EntityManagerAutoBid entityManagerAutoBid = new EntityManagerAutoBid();
        List<EntityAutoBid> bids = entityManagerAutoBid.getProductAutoBids(dtoAutobid.getEntityAutoBid().getProductId(), dtoAutobid.getOffset(), dtoAutobid.getLimit());
        int totalBids = entityManagerAutoBid.getProductTotalAutoBids(dtoAutobid.getEntityAutoBid().getProductId());
        
        List<EntityAutoBid> bidList = new ArrayList<>();
        TimeUtils timeUtils = new TimeUtils();
        for(EntityAutoBid entityAutoBid : bids)
        {
            entityAutoBid.setCreatedTime(timeUtils.convertUnixToHuman(entityAutoBid.getCreatedOn(), ""));
            bidList.add(entityAutoBid);
        }        
        clientResponse.setList(bidList);
        clientResponse.setCounter(totalBids);
        clientResponse.setSuccess(true);
        return clientResponse;
    }
}
