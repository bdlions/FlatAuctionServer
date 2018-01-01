package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityPet;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerPet {
    
    public List<EntityPet> getAllPets() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityPet> query = session.getNamedQuery("getAllPets");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
