package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntitySavedProduct;
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
public class EntityManagerSavedProduct 
{
    private final Logger logger = LoggerFactory.getLogger(EntityManagerSavedProduct.class);
    public EntitySavedProduct getUserSavedProductByProductId(int userId, int productId, Session session)
    {
        Query<EntitySavedProduct> query = session.getNamedQuery("getUserSavedProductByProductId");   
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);
        List<EntitySavedProduct> productList = query.getResultList();
        if(productList == null || productList.isEmpty())
        {
            return null;
        }
        else
        {
            return productList.get(0);
        }  
    }
    public EntitySavedProduct getUserSavedProductByProductId(int userId, int productId)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            return getUserSavedProductByProductId(userId, productId, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public EntitySavedProduct createSavedProduct(EntitySavedProduct entityUserProduct, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityUserProduct.setCreatedOn(timeUtils.getCurrentTime());
        entityUserProduct.setModifiedOn(timeUtils.getCurrentTime());
        session.save(entityUserProduct);
        return entityUserProduct;
    }
    public EntitySavedProduct createSavedProduct(EntitySavedProduct entityUserProduct)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return createSavedProduct(entityUserProduct, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public List<EntitySavedProduct> getSavedProductsByUserId(int userId, int offset, int limit, Session session)
    {
        Query<EntitySavedProduct> query = session.getNamedQuery("getSavedProductsByUserId");   
        query.setParameter("userId", userId);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }    
    public List<EntitySavedProduct> getSavedProductsByUserId(int userId, int offset, int limit)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            return getSavedProductsByUserId(userId, offset, limit, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public int getTotalSavedProductsByUserId(int userId, Session session)
    {
        Query<EntitySavedProduct> query = session.getNamedQuery("getSavedProductsByUserId");   
        query.setParameter("userId", userId);
        return query.getResultList().size();
    } 
    public int getTotalSavedProductsByUserId(int userId)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            return getTotalSavedProductsByUserId(userId, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public int deleteSavedProductByUserIdProductId(int userId, int productId)
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.getTransaction(); 
        try 
        {   
            tx.begin();
            Query<EntitySavedProduct> query = session.getNamedQuery("deleteSavedProductByUserIdProductId");   
            query.setParameter("userId", userId);
            query.setParameter("productId", productId);
            query.executeUpdate();
            tx.commit();
            return 1;
        } 
        catch(Exception ex)
        {
            logger.debug(ex.toString());
            tx.rollback();
            return 0;
        }
        finally 
        {
            session.close();
        }
    }
}
