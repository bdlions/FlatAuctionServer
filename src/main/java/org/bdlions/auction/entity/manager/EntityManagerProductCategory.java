package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityProductCategory;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerProductCategory {
    
    public List<EntityProductCategory> getAllCategories() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityProductCategory> query = session.getNamedQuery("getAllCategories");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
