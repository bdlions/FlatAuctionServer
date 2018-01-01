package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityMessageBody;
import org.bdlions.auction.util.TimeUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerMessageBody {
    public EntityMessageBody createMessageBody(EntityMessageBody entityMessageBody, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityMessageBody.setCreatedOn(timeUtils.getCurrentTime());
        entityMessageBody.setModifiedOn(timeUtils.getCurrentTime());
        session.save(entityMessageBody);
        return entityMessageBody;
    }
    
    public EntityMessageBody createMessageBody(EntityMessageBody entityMessageBody)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return createMessageBody(entityMessageBody, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityMessageBody> getMessageBodyList(int messageHeaderId, int offset ,int limit)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityMessageBody> query = session.getNamedQuery("getMessageBodyList");
            query.setParameter("messageHeaderId", messageHeaderId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();                      
        }
        finally 
        {
            session.close();
        }
    }
    public int getMessageTotalBodyList(int messageHeaderId)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityMessageBody> query = session.getNamedQuery("getMessageBodyList");
            query.setParameter("messageHeaderId", messageHeaderId);
            return query.getResultList().size();                      
        }
        finally 
        {
            session.close();
        }
    }
}
