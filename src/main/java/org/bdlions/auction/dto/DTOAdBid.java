package org.bdlions.auction.dto;

import java.util.List;
import org.bdlions.auction.entity.EntityProduct;

/**
 *
 * @author Nazmul Hasan
 */
public class DTOAdBid {
    private List<EntityProduct> entityProductList;

    public List<EntityProduct> getEntityProductList() {
        return entityProductList;
    }

    public void setEntityProductList(List<EntityProduct> entityProductList) {
        this.entityProductList = entityProductList;
    }    
}
