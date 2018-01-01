package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityLocation;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerLocation {
    public List<EntityLocation> getAllLocations() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityLocation> query = session.getNamedQuery("getAllLocations");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
