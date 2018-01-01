package org.bdlions.request.handler;

import com.bdlions.dto.Credential;
import org.bdlions.transport.packet.IPacket;
import org.bdlions.session.ISession;
import org.bdlions.session.ISessionManager;
import com.bdlions.util.ACTION;
import com.bdlions.commons.ClientMessages;
import com.bdlions.dto.response.ClientListResponse;
import com.bdlions.dto.response.ClientResponse;
import com.bdlions.dto.response.SignInResponse;
import org.bdlions.util.StringUtils;
import org.bdlions.util.annotation.ClientRequest;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.bdlions.auction.dto.DTOBid;
import org.bdlions.auction.dto.DTOProduct;
import org.bdlions.auction.dto.DTOUser;
import org.bdlions.auction.entity.EntityBid;
import org.bdlions.auction.entity.EntityProduct;
import org.bdlions.auction.entity.EntityRole;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.EntitySavedProduct;
import org.bdlions.auction.entity.EntityUserRole;
import org.bdlions.auction.entity.manager.EntityManagerBid;
import org.bdlions.auction.entity.manager.EntityManagerProduct;
import org.bdlions.auction.entity.manager.EntityManagerUser;
import org.bdlions.auction.entity.manager.EntityManagerSavedProduct;

//import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author nazmul hasan
 */
public class SavedProductHandler {

    private final ISessionManager sessionManager;

    public SavedProductHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ClientRequest(action = ACTION.ADD_SAVED_PRODUCT)
    public ClientResponse addSavedProduct(ISession session, IPacket packet) throws Exception 
    {
        ClientResponse clientResponse = new ClientResponse();
        Gson gson = new Gson();
        EntitySavedProduct entityUserProduct = gson.fromJson(packet.getPacketBody(), EntitySavedProduct.class);
        if(entityUserProduct == null )
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid ad info to be saved. Please try again later.");
            return clientResponse;
        }
        int userId = (int)session.getUserId();
        entityUserProduct.setUserId(userId);
        EntityManagerSavedProduct entityManagerUserProduct = new EntityManagerSavedProduct();
        EntitySavedProduct tempEntityUserProduct = entityManagerUserProduct.getUserSavedProductByProductId(userId, entityUserProduct.getProductId());
        if(tempEntityUserProduct == null)
        {
            entityManagerUserProduct.createSavedProduct(entityUserProduct);
            clientResponse.setSuccess(true);
            clientResponse.setMessage("Ad is saved successfully.");
        }
        else
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("The ad is already in your saved ad list.");
        }        
        return clientResponse;
    }
    
    @ClientRequest(action = ACTION.FETCH_SAVED_PRODUCT_LIST)
    public ClientResponse getSavedProductList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientResponse = new ClientListResponse();
        List<EntityProduct> resultEntityProductList = new ArrayList<>();
        Gson gson = new Gson();
        DTOProduct dtoProduct = gson.fromJson(packet.getPacketBody(), DTOProduct.class);
        if(dtoProduct == null)
        {
            clientResponse.setSuccess(false);
            clientResponse.setMessage("Invalid request with offset and limit.");
            return clientResponse;
        }
        
        int userId = (int)session.getUserId();
        EntityManagerSavedProduct entityManagerUserProduct = new EntityManagerSavedProduct();
        List<EntitySavedProduct> entityUserProductList = entityManagerUserProduct.getSavedProductsByUserId(userId, dtoProduct.getOffset(), dtoProduct.getLimit());
        if(entityUserProductList != null && !entityUserProductList.isEmpty())
        {
            List<Integer> productIdList = new ArrayList<>();
            for(EntitySavedProduct entityUserProduct: entityUserProductList)
            {
                productIdList.add(entityUserProduct.getProductId());
            }
            if(!productIdList.isEmpty())
            {
                
                EntityManagerProduct entityManagerProduct = new EntityManagerProduct();
                List<EntityProduct> entityProductList = entityManagerProduct.getProductsByProductIds(productIdList);
                for(EntityProduct entityProduct: entityProductList)
                {
                    EntityProduct tempEntityProduct = new EntityProduct();
                    tempEntityProduct.setId(entityProduct.getId());
                    tempEntityProduct.setReferenceId(entityProduct.getReferenceId());
                    tempEntityProduct.setTitle(entityProduct.getTitle());
                    tempEntityProduct.setDescription(entityProduct.getDescription());
                    tempEntityProduct.setBasePrice(entityProduct.getBasePrice());
                    tempEntityProduct.setImg(entityProduct.getImg());
                    resultEntityProductList.add(tempEntityProduct);
                }
            }
        }
        clientResponse.setList(resultEntityProductList);
        clientResponse.setCounter(entityManagerUserProduct.getTotalSavedProductsByUserId(userId));
        clientResponse.setSuccess(true);
        return clientResponse;
    }
}
