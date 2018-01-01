package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityProductType;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerProductType {
    
    public List<EntityProductType> getAllTypes() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityProductType> query = session.getNamedQuery("getAllTypes");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
