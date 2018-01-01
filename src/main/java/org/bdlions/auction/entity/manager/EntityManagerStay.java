package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityStay;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerStay {
    
    public List<EntityStay> getAllStays() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityStay> query = session.getNamedQuery("getAllStays");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
