package org.bdlions.auction.dto;

import com.bdlions.dto.response.ClientResponse;
import java.util.List;
import org.bdlions.auction.entity.EntityAccountStatus;
import org.bdlions.auction.entity.EntityRole;
import org.bdlions.auction.entity.EntityUser;

/**
 *
 * @author Nazmul Hasan
 */
public class DTOUser implements java.io.Serializable {
    public EntityUser entityUser;
    public List<EntityRole> roles;
    public EntityAccountStatus entityAccountStatus;

    public EntityUser getEntityUser() {
        return entityUser;
    }

    public void setEntityUser(EntityUser entityUser) {
        this.entityUser = entityUser;
    }

    public List<EntityRole> getRoles() {
        return roles;
    }

    public void setRoles(List<EntityRole> roles) {
        this.roles = roles;
    }    
}
