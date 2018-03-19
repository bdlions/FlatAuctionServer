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
import org.bdlions.auction.dto.DTOLocation;
import org.bdlions.auction.entity.EntityLocation;
import org.bdlions.auction.entity.EntityMessageBody;
import org.bdlions.auction.entity.manager.EntityManagerLocation;
import org.bdlions.auction.entity.manager.EntityManagerMessageBody;
import org.bdlions.util.StringUtils;

//import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author nazmul hasan
 */
public class AdminLocationHandler {

    private final ISessionManager sessionManager;

    public AdminLocationHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ClientRequest(action = ACTION.FETCH_LOCATIONS)
    public ClientResponse getLocations(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        Gson gson = new Gson();
        DTOLocation dtoLocation = gson.fromJson(packet.getPacketBody(), DTOLocation.class);
        if(dtoLocation == null)
        {
            clientListResponse.setSuccess(false);
            clientListResponse.setMessage("Invalid request to get location list. Please try again later.");
            return clientListResponse;
        }
        EntityManagerLocation entityManagerLocation = new EntityManagerLocation();
        List<EntityLocation> entityLocationList = entityManagerLocation.getLocations(dtoLocation.getOffset(), dtoLocation.getLimit());
        int totalLocations = entityManagerLocation.getTotalLocations();
        clientListResponse.setList(entityLocationList);
        clientListResponse.setCounter(totalLocations);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_LOCATION_INFO)
    public ClientResponse getLocationInfo(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntityLocation entityLocation = gson.fromJson(packet.getPacketBody(), EntityLocation.class);
        if(entityLocation == null || entityLocation.getId() == 0)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid request to get location info. Please try again later.");
            return clientResponse;
        }
        EntityManagerLocation entityManagerLocation = new EntityManagerLocation();        
        EntityLocation resultEntityLocation = entityManagerLocation.getLocationById(entityLocation.getId());
        if(resultEntityLocation != null && resultEntityLocation.getId() > 0)
        {
            clientResponse.setSuccess(true);
            clientResponse.setResult(resultEntityLocation);
            clientResponse.setMessage("Location is added successfully.");
        }
        else
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Location doesn't exist or invalid. Please try again later.");
        }        
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.ADD_LOCATION)
    public ClientResponse addLocation(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntityLocation entityLocation = gson.fromJson(packet.getPacketBody(), EntityLocation.class);
        if(entityLocation == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid location info. Please try again later.");
            return clientResponse;
        }
        if(StringUtils.isNullOrEmpty(entityLocation.getSearchString()))
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid title of location.");
            return clientResponse;
        }
        EntityManagerLocation entityManagerLocation = new EntityManagerLocation();        
        EntityLocation tempEntityLocation = entityManagerLocation.getLocationBySearchString(entityLocation.getSearchString());
        if(tempEntityLocation != null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Title of the location in invalid or already exists.");
            return clientResponse;
        }
        
        EntityLocation resultEntityLocation = entityManagerLocation.addLocation(entityLocation);
        if(resultEntityLocation != null && resultEntityLocation.getId() > 0)
        {
            clientResponse.setSuccess(true);
            clientResponse.setResult(resultEntityLocation);
            clientResponse.setMessage("Location is added successfully.");
        }
        else
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Unable to add location. Please try again later.");
        }        
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.UPDATE_LOCATION)
    public ClientResponse updateLocation(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntityLocation entityLocation = gson.fromJson(packet.getPacketBody(), EntityLocation.class);
        if(entityLocation == null || entityLocation.getId() <= 0)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid location info. Please try again later.");
            return clientResponse;
        }
        if(StringUtils.isNullOrEmpty(entityLocation.getSearchString()))
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid title of location.");
            return clientResponse;
        }
        EntityManagerLocation entityManagerLocation = new EntityManagerLocation();
        EntityLocation tempEntityLocation = entityManagerLocation.getLocationBySearchString(entityLocation.getSearchString());
        if(tempEntityLocation != null && tempEntityLocation.getId() != entityLocation.getId())
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Title of the location in invalid or already exists.");
            return clientResponse;
        }
        if(entityManagerLocation.updateLocation(entityLocation))
        {
            clientResponse.setSuccess(true);
            clientResponse.setMessage("Location is updated successfully.");
        }
        else
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Unable to add location. Please try again later.");
        }        
        return clientResponse;
    }
}
