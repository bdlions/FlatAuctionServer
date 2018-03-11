package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityGender;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerGender 
{
    public List<EntityGender> getAllGenders()
    {
        Session session = HibernateUtil.getSession();
        try
        {
            return getAllGenders(session);
        }
        finally
        {
            session.close();
        }
    }
    
    public List<EntityGender> getAllGenders(Session session)
    {
        Query<EntityGender> query = session.getNamedQuery("getAllGenders");
        return query.getResultList();
    }
}
