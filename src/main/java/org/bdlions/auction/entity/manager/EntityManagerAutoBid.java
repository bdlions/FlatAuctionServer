package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityAutoBid;
import org.bdlions.auction.util.TimeUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerAutoBid {
    
    public EntityAutoBid createAutoBid(EntityAutoBid entityAutoBid, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityAutoBid.setCreatedOn(timeUtils.getCurrentTime());
        entityAutoBid.setModifiedOn(timeUtils.getCurrentTime());
        session.save(entityAutoBid);
        return entityAutoBid;
    }
    
    public EntityAutoBid createAutoBid(EntityAutoBid entityAutoBid)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return createAutoBid(entityAutoBid, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityAutoBid> getProductAutoBids(int productId, int offset, int limit) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityAutoBid> query = session.getNamedQuery("getAutoBidsByProductId");
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
    
    public int getProductTotalAutoBids(int productId) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityAutoBid> query = session.getNamedQuery("getAutoBidsByProductId");
            query.setParameter("productId", productId);
            return query.getResultList().size();
        } 
        finally 
        {
            session.close();
        }
    }
}
