package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityProductSize;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerProductSize {
    
    public List<EntityProductSize> getAllSizes() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityProductSize> query = session.getNamedQuery("getAllSizes");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
