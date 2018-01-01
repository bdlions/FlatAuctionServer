package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityOccupation;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerOccupation {
    
    public List<EntityOccupation> getAllOccupations() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityOccupation> query = session.getNamedQuery("getAllOccupations");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
