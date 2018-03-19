package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityLocation;
import org.bdlions.auction.entity.EntityProduct;
import org.bdlions.auction.util.TimeUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerLocation {
    private final Logger logger = LoggerFactory.getLogger(EntityManagerLocation.class);
    
    public EntityLocation getLocationById(int locationId)
    {
        Session session = HibernateUtil.getSession();
        try {
            
            Query<EntityLocation> query = session.getNamedQuery("getLocationById");
            query.setParameter("locationId", locationId);            
            List<EntityLocation> locationList = query.getResultList();
            if(locationList == null || locationList.isEmpty())
            {
                return null;
            }
            else
            {
                return locationList.get(0);
            }           
        }
        finally 
        {
            session.close();
        }
    }
    
    public EntityLocation getLocationBySearchString(String searchString)
    {
        Session session = HibernateUtil.getSession();
        try {
            
            Query<EntityLocation> query = session.getNamedQuery("getLocationBySearchString");
            query.setParameter("searchString", searchString);            
            List<EntityLocation> locationList = query.getResultList();
            if(locationList == null || locationList.isEmpty())
            {
                return null;
            }
            else
            {
                return locationList.get(0);
            }           
        }
        finally 
        {
            session.close();
        }
    }
    
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
    
    public EntityLocation addLocation(EntityLocation entityLocation, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityLocation.setCreatedOn(timeUtils.getCurrentTime());
        entityLocation.setModifiedOn(timeUtils.getCurrentTime());
        session.save(entityLocation);
        return entityLocation;
    }
    
    public EntityLocation addLocation(EntityLocation entityLocation)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return addLocation(entityLocation, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public boolean updateLocation(EntityLocation entityLocation, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityLocation.setModifiedOn(timeUtils.getCurrentTime());
        session.update(entityLocation);
        return true;
    }
    
    public boolean updateLocation(EntityLocation entityLocation)
    {
        Session session = HibernateUtil.getSession();        
        session.clear();
        Transaction tx = session.getTransaction(); 
        try 
        {
            tx.begin();
            updateLocation(entityLocation, session);
            tx.commit();
            return true;
        } 
        catch(Exception ex)
        {
            logger.error(ex.toString());
            tx.rollback();
            return false;
        }
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityLocation> getLocations(int offset, int limit) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityLocation> query = session.getNamedQuery("getAllLocations");
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
    public int getTotalLocations() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityLocation> query = session.getNamedQuery("getAllLocations");
            return query.getResultList().size();
        } 
        finally 
        {
            session.close();
        }
    }
}
