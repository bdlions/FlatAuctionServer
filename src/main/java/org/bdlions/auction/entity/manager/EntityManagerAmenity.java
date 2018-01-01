package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityAmenity;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerAmenity {
    
    public List<EntityAmenity> getAllAmenities() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityAmenity> query = session.getNamedQuery("getAllAmenities");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
