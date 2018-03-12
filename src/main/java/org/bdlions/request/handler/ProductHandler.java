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
import org.bdlions.auction.dto.DTOProduct;
import org.bdlions.auction.dto.DTOSearchParam;
import org.bdlions.auction.entity.EntityProduct;
import org.bdlions.auction.entity.EntityTime;
import org.bdlions.auction.entity.manager.EntityManagerAmenity;
import org.bdlions.auction.entity.manager.EntityManagerAvailability;
import org.bdlions.auction.entity.manager.EntityManagerLocation;
import org.bdlions.auction.entity.manager.EntityManagerProduct;
import org.bdlions.auction.entity.manager.EntityManagerProductCategory;
import org.bdlions.auction.entity.manager.EntityManagerProductSize;
import org.bdlions.auction.entity.manager.EntityManagerProductType;
import org.bdlions.auction.entity.manager.EntityManagerSmoking;
import org.bdlions.auction.entity.manager.EntityManagerStay;
import org.bdlions.auction.entity.manager.EntityManagerOccupation;
import org.bdlions.auction.entity.manager.EntityManagerPet;
import org.bdlions.auction.entity.manager.EntityManagerTime;
import org.bdlions.auction.util.StringUtils;
import org.bdlions.auction.util.TimeUtils;
import org.bdlions.library.ProductLibrary;

//import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author nazmul hasan
 */
public class ProductHandler {

    private final ISessionManager sessionManager;

    public ProductHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ClientRequest(action = ACTION.FETCH_PRODUCT_CATEGORY_LIST)
    public ClientResponse getProductCategories(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerProductCategory entityManagerProductCategory = new EntityManagerProductCategory();
        clientListResponse.setList(entityManagerProductCategory.getAllCategories());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_PRODUCT_SIZE_LIST)
    public ClientResponse getProductSizes(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerProductSize entityManagerProductSize = new EntityManagerProductSize();
        clientListResponse.setList(entityManagerProductSize.getAllSizes());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_PRODUCT_TYPE_LIST)
    public ClientResponse getProductTypes(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerProductType entityManagerProductType = new EntityManagerProductType();
        clientListResponse.setList(entityManagerProductType.getAllTypes());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_STAY_LIST)
    public ClientResponse getStays(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerStay entityManagerProductStay = new EntityManagerStay();
        clientListResponse.setList(entityManagerProductStay.getAllStays());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_SMOKING_LIST)
    public ClientResponse getSmokings(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerSmoking entityManagerSmoking = new EntityManagerSmoking();
        clientListResponse.setList(entityManagerSmoking.getAllSmokings());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_OCCUPATION_LIST)
    public ClientResponse getOccupations(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerOccupation entityManagerOccupation = new EntityManagerOccupation();
        clientListResponse.setList(entityManagerOccupation.getAllOccupations());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_PET_LIST)
    public ClientResponse getPets(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerPet entityManagerPet = new EntityManagerPet();
        clientListResponse.setList(entityManagerPet.getAllPets());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_LOCATION_LIST)
    public ClientResponse getLocations(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerLocation entityManagerLocation = new EntityManagerLocation();
        clientListResponse.setList(entityManagerLocation.getAllLocations());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_AMENITY_LIST)
    public ClientResponse getAmenities(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerAmenity entityManagerAmenity = new EntityManagerAmenity();
        clientListResponse.setList(entityManagerAmenity.getAllAmenities());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_AVAILABILITY_LIST)
    public ClientResponse getAvailabilities(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerAvailability entityManagerAvailability = new EntityManagerAvailability();
        clientListResponse.setList(entityManagerAvailability.getAllAvailabilites());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_TIME_LIST)
    public ClientResponse getTimes(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerTime entityManagerTime = new EntityManagerTime();
        clientListResponse.setList(entityManagerTime.getAllTimes());
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.ADD_PRODUCT_INFO)
    public ClientResponse addProductInfo(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        DTOProduct dtoProduct = gson.fromJson(packet.getPacketBody(), DTOProduct.class);
        if(dtoProduct == null || dtoProduct.getEntityProduct() == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid ad info. Please try again later.");
            return clientResponse;
        }
        if( StringUtils.isNullOrEmpty(dtoProduct.getEntityProduct().getTitle()) )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Add title is required. Please try again later.");
            return clientResponse;
        }
        //check unique reference id before adding product
        int userId = (int)session.getUserId();
        dtoProduct.getEntityProduct().setReferenceId(org.bdlions.auction.util.StringUtils.getProductReferenceId());
        dtoProduct.getEntityProduct().setUserId(userId);
        dtoProduct.getEntityProduct().setTotalBids(0);
        
        EntityManagerTime entityManagerTime = new EntityManagerTime();
        EntityTime entityTimeStart = entityManagerTime.getTimeInfo(dtoProduct.getEntityProduct().getAuctionStartTimeId());
        EntityTime entityTimeEnd = entityManagerTime.getTimeInfo(dtoProduct.getEntityProduct().getAuctionEndTimeId());
        TimeUtils timeUtils = new TimeUtils();
        long unixStartDate = timeUtils.getHumanToUnix(dtoProduct.getEntityProduct().getAuctionStartDate(), "");
        long unixEndDate = timeUtils.getHumanToUnix(dtoProduct.getEntityProduct().getAuctionEndDate(), "");
        dtoProduct.getEntityProduct().setUnixAuctionStart(unixStartDate + entityTimeStart.getSec());
        dtoProduct.getEntityProduct().setUnixAuctionEnd(unixEndDate + entityTimeEnd.getSec());
        
        
        EntityManagerProduct entityManagerProduct = new EntityManagerProduct();
        EntityProduct entityProduct = entityManagerProduct.createProduct(dtoProduct.getEntityProduct());
        if(entityProduct != null && entityProduct.getId() > 0)
        {
            String images = dtoProduct.getImages();
            if(!StringUtils.isNullOrEmpty(images))
            {
                ProductLibrary productLibrary = new ProductLibrary();
                productLibrary.copyUploadedImages(images);
            }            
            clientResponse.setSuccess(true);
            clientResponse.setResult(entityProduct);
        }
        else
        {
            clientResponse.setSuccess(false);
        }
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_PRODUCT_INFO)
    public ClientResponse getProductInfo(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntityProduct reqEntityProduct = gson.fromJson(packet.getPacketBody(), EntityProduct.class);
        if(reqEntityProduct == null || reqEntityProduct.getId() <= 0)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid request to get ad info. Please try again later.");
            return clientResponse;
        }
        
        EntityManagerProduct entityManagerProduct = new EntityManagerProduct();
        EntityProduct entityProduct = entityManagerProduct.getProductById(reqEntityProduct.getId());        
        if(entityProduct != null && entityProduct.getId() > 0)
        {
            TimeUtils timeUtils = new TimeUtils();
            long currentTime = timeUtils.getCurrentTime();
            DTOProduct dtoProduct = new DTOProduct();
            dtoProduct.setEntityProduct(entityProduct);
            dtoProduct.setAuctionEndTimeLeft(entityProduct.getUnixAuctionEnd() - currentTime);
        
            clientResponse.setSuccess(true);
            clientResponse.setResult(dtoProduct);
        }
        else
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Ad doesn't exist or invalid.");
        }
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.UPDATE_PRODUCT_INFO)
    public ClientResponse updateProductInfo(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        DTOProduct dtoProduct = gson.fromJson(packet.getPacketBody(), DTOProduct.class);
        if(dtoProduct == null || dtoProduct.getEntityProduct() == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid ad info. Please try again later.");
            return clientResponse;
        }
        if( dtoProduct.getEntityProduct().getId() <=0 )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid ad to update. Please try again later.");
            return clientResponse;
        }
        if( StringUtils.isNullOrEmpty(dtoProduct.getEntityProduct().getTitle()) )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Add title is required. Please try again later.");
            return clientResponse;
        }
        
        EntityManagerTime entityManagerTime = new EntityManagerTime();
        EntityTime entityTimeStart = entityManagerTime.getTimeInfo(dtoProduct.getEntityProduct().getAuctionStartTimeId());
        EntityTime entityTimeEnd = entityManagerTime.getTimeInfo(dtoProduct.getEntityProduct().getAuctionEndTimeId());
        TimeUtils timeUtils = new TimeUtils();
        long unixStartDate = timeUtils.getHumanToUnix(dtoProduct.getEntityProduct().getAuctionStartDate(), "");
        long unixEndDate = timeUtils.getHumanToUnix(dtoProduct.getEntityProduct().getAuctionEndDate(), "");
        dtoProduct.getEntityProduct().setUnixAuctionStart(unixStartDate + entityTimeStart.getSec());
        dtoProduct.getEntityProduct().setUnixAuctionEnd(unixEndDate + entityTimeEnd.getSec());
        
        //check unique reference id before updating product
        EntityManagerProduct entityManagerProduct = new EntityManagerProduct();
        if(entityManagerProduct.updateProduct(dtoProduct.getEntityProduct()))
        {
            String images = dtoProduct.getImages();
            if(!StringUtils.isNullOrEmpty(images))
            {
                ProductLibrary productLibrary = new ProductLibrary();
                productLibrary.copyUploadedImages(images);
            } 
            clientResponse.setSuccess(true);
            clientResponse.setMessage("Ad is updated successfully.");
        }
        else
        {
            clientResponse.setSuccess(false);
        }
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_USER_PRODUCT_LIST)
    public ClientResponse getUserProductList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        Gson gson = new Gson();
        DTOProduct dtoProduct = gson.fromJson(packet.getPacketBody(), DTOProduct.class);
        if(dtoProduct == null )
        {
            clientListResponse.setSuccess(false);
            clientListResponse.setMessage("Invalid request to get ad list. Please try again later.");
            return clientListResponse;
        }
        int userId = 0;
        if(dtoProduct.getEntityProduct() == null || dtoProduct.getEntityProduct().getUserId() == 0)
        {
            userId = (int)session.getUserId();
        }
        else
        {
            userId = dtoProduct.getEntityProduct().getUserId();
        }
        
        EntityManagerProduct entityManagerProduct = new EntityManagerProduct();
        List<EntityProduct> entityProductList = entityManagerProduct.getUserProducts(userId, dtoProduct.getOffset(), dtoProduct.getLimit());
        int totalProducts = entityManagerProduct.getUserTotalProducts(userId);
        clientListResponse.setList(entityProductList);
        clientListResponse.setCounter(totalProducts);
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_CLOSING_PRODUCT_LIST)
    public ClientResponse getClosingProductList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        EntityManagerProduct entityManagerProduct = new EntityManagerProduct();
        List<EntityProduct> entityProductList = entityManagerProduct.getClosingProducts();
        TimeUtils timeUtils = new TimeUtils();
        long currentTime = timeUtils.getCurrentTime();
        List<DTOProduct> products = new ArrayList<>();
        for(EntityProduct entityProduct : entityProductList)
        {
            DTOProduct dtoProduct = new DTOProduct();
            dtoProduct.setAuctionEndTimeLeft(entityProduct.getUnixAuctionEnd() - currentTime);
            dtoProduct.setEntityProduct(entityProduct);
            products.add(dtoProduct);
        }
        clientListResponse.setList(products);
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
    
    @ClientRequest(action = ACTION.SEARCH_PRODUCT_LIST)
    public ClientResponse searchProductList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        Gson gson = new Gson();
        DTOSearchParam dtoSearchParam = gson.fromJson(packet.getPacketBody(), DTOSearchParam.class);     
        if(dtoSearchParam == null)
        {
            clientListResponse.setSuccess(false);
            clientListResponse.setMessage("Invalid search params.");
            return clientListResponse;
        }
        EntityManagerProduct entityManagerProduct = new EntityManagerProduct();
        List<EntityProduct> entityProductList = entityManagerProduct.searchProduct(dtoSearchParam, dtoSearchParam.getOffset(), dtoSearchParam.getLimit());
        clientListResponse.setList(entityProductList);
        clientListResponse.setCounter(entityManagerProduct.searchTotalProduct(dtoSearchParam));
        clientListResponse.setSuccess(true);
        return clientListResponse;
    }
}
