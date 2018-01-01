package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityAvailability;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerAvailability {
    
    public List<EntityAvailability> getAllAvailabilites() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityAvailability> query = session.getNamedQuery("getAllAvailabilities");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
