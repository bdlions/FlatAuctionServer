package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityMessageBody;
import org.bdlions.auction.entity.EntityMessageHeader;
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
public class EntityManagerMessageHeader 
{
    private final Logger logger = LoggerFactory.getLogger(EntityManagerMessageHeader.class);
    
    public EntityMessageHeader getMessageHeaderById(int id)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityMessageHeader> query = session.getNamedQuery("getMessageHeaderById");
            query.setParameter("id", id);
            List<EntityMessageHeader> messageHeaderList = query.getResultList();
            if(messageHeaderList == null || messageHeaderList.isEmpty())
            {
                return null;
            }
            else
            {
                return messageHeaderList.get(0);
            }           
        }
        finally 
        {
            session.close();
        }
    }
    
    public EntityMessageHeader getProductMessageHeaderByUserId(int senderUserId, int productId)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityMessageHeader> query = session.getNamedQuery("getProductMessageHeaderByUserId");
            query.setParameter("senderUserId", senderUserId);
            query.setParameter("productId", productId);
            List<EntityMessageHeader> messageHeaderList = query.getResultList();
            if(messageHeaderList == null || messageHeaderList.isEmpty())
            {
                return null;
            }
            else
            {
                return messageHeaderList.get(0);
            }           
        }
        finally 
        {
            session.close();
        }
    }
    
    public EntityMessageHeader createMessageHeader(EntityMessageHeader entityMessageHeader, EntityMessageBody entityMessageBody)
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.getTransaction(); 
        tx.begin();
        try 
        {
            TimeUtils timeUtils = new TimeUtils();
            entityMessageHeader.setCreatedOn(timeUtils.getCurrentTime());
            entityMessageHeader.setModifiedOn(timeUtils.getCurrentTime());
            session.save(entityMessageHeader);
            if(entityMessageBody != null && entityMessageHeader.getId() > 0)
            {
                entityMessageBody.setMessageHeaderId(entityMessageHeader.getId());
                EntityManagerMessageBody entityManagerMessageBody = new EntityManagerMessageBody();
                entityManagerMessageBody.createMessageBody(entityMessageBody, session);
            }
            tx.commit();
            return entityMessageHeader;
        }
        catch(Exception ex)
        {
            logger.error(ex.toString());
            tx.rollback();
            return null;
        }
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityMessageHeader> getSentMessageList(int senderUserId, int offset ,int limit)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityMessageHeader> query = session.getNamedQuery("getSentMessageList");
            query.setParameter("senderUserId", senderUserId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();                      
        }
        finally 
        {
            session.close();
        }
    }
    public int getTotalSentMessageList(int senderUserId)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityMessageHeader> query = session.getNamedQuery("getSentMessageList");
            query.setParameter("senderUserId", senderUserId);
            return query.getResultList().size();                      
        }
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityMessageHeader> getInboxMessageList(int userId, int offset ,int limit)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityMessageHeader> query = session.getNamedQuery("getInboxMessageList");
            query.setParameter("senderUserId", userId);
            query.setParameter("receiverUserId", userId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();                      
        }
        finally 
        {
            session.close();
        }
    }
    public int getTotalInboxMessageList(int userId)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityMessageHeader> query = session.getNamedQuery("getInboxMessageList");
            query.setParameter("senderUserId", userId);
            query.setParameter("receiverUserId", userId);
            return query.getResultList().size();                      
        }
        finally 
        {
            session.close();
        }
    }
}
