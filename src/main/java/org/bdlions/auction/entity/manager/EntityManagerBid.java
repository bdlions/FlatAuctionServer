package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityBid;
import org.bdlions.auction.entity.EntityProduct;
import org.bdlions.auction.util.TimeUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerBid {
    
    public EntityBid createBid(EntityBid entityBid, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityBid.setCreatedOn(timeUtils.getCurrentTime());
        entityBid.setModifiedOn(timeUtils.getCurrentTime());
        session.save(entityBid);
        return entityBid;
    }
    
    public EntityBid createBid(EntityBid entityBid)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return createBid(entityBid, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityBid> getProductBids(int productId, int offset, int limit) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityBid> query = session.getNamedQuery("getBidsByProductId");
            query.setParameter("productId", productId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
    
    public int getProductTotalBids(int productId) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityProduct> query = session.getNamedQuery("getBidsByProductId");
            query.setParameter("productId", productId);
            return query.getResultList().size();
        } 
        finally 
        {
            session.close();
        }
    }
}
