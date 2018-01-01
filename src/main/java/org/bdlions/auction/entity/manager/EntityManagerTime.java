package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityTime;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerTime {
    
    public List<EntityTime> getAllTimes() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityTime> query = session.getNamedQuery("getAllTimes");
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
    
    public EntityTime getTimeInfo(int timeId, Session session) 
    {
        Query<EntityTime> query = session.getNamedQuery("getTimeById");
        query.setParameter("timeId", timeId);
        List<EntityTime> timeList = query.getResultList();
        if(timeList == null || timeList.isEmpty())
        {
            return null;
        }
        else
        {
            return timeList.get(0);
        } 
    }
    
    public EntityTime getTimeInfo(int timeId) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return getTimeInfo(timeId, session);
        } 
        finally 
        {
            session.close();
        }
    }
}
