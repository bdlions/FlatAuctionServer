package org.bdlions.auction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author nazmul hasan
 */
@Entity
@Table(
        name = "saved_products",
        indexes = {
            @Index(name = "idx_user_id_product_id", columnList = "user_id, product_id", unique = true)
        }
)
@NamedQueries({
    @NamedQuery(
            name = "getSavedProductsByUserId", 
            query = "from EntitySavedProduct product where product.userId = :userId"
    ),
    @NamedQuery(
            name = "getUserSavedProductByProductId", 
            query = "from EntitySavedProduct product where product.userId = :userId and product.productId = :productId"
    )
})
public class EntitySavedProduct {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")    
    private int id;

    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "product_id")
    private int productId;
    
    @Column(name = "created_on", length = 11, columnDefinition = "int(11) unsigned DEFAULT 0")
    private long createdOn;

    @Column(name = "modified_on", length = 11, columnDefinition = "int(11) unsigned DEFAULT 0")
    private long modifiedOn;

    public EntitySavedProduct() 
    {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(long modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
    
}
