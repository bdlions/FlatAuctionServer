package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntitySmoking;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerSmoking {
    
    public List<EntitySmoking> getAllSmokings() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntitySmoking> query = session.getNamedQuery("getAllSmokings");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
