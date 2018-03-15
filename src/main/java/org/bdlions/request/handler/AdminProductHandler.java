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
import org.bdlions.auction.entity.EntityProduct;
import org.bdlions.auction.entity.manager.EntityManagerProduct;

//import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author nazmul hasan
 */
public class AdminProductHandler {

    private final ISessionManager sessionManager;

    public AdminProductHandler(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ClientRequest(action = ACTION.FETCH_PRODUCT_LIST)
    public ClientResponse getProductList(ISession session, IPacket packet) throws Exception 
    {
        ClientListResponse clientListResponse = new ClientListResponse();
        Gson gson = new Gson();
        DTOProduct dtoProduct = gson.fromJson(packet.getPacketBody(), DTOProduct.class);
        if(dtoProduct == null)
        {
            clientListResponse.setSuccess(false);
            clientListResponse.setMessage("Invalid request to get product list. Please try again later.");
            return clientListResponse;
        }
        EntityManagerProduct entityManagerProduct = new EntityManagerProduct();
        List<EntityProduct> entityProductList = entityManagerProduct.getProducts(dtoProduct.getOffset(), dtoProduct.getLimit());
        int totalProducts = entityManagerProduct.getTotalProducts();
        clientListResponse.setList(entityProductList);
        clientListResponse.setCounter(totalProducts);
        return clientListResponse;
    }
}
