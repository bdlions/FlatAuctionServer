package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
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
public class EntityManagerProduct {
    private final Logger logger = LoggerFactory.getLogger(EntityManagerProduct.class);
    public EntityProduct getProductById(int productId)
    {
        Session session = HibernateUtil.getSession();
        try {
            
            Query<EntityProduct> query = session.getNamedQuery("getProductById");
            query.setParameter("productId", productId);            
            List<EntityProduct> productList = query.getResultList();
            if(productList == null || productList.isEmpty())
            {
                return null;
            }
            else
            {
                return productList.get(0);
            }           
        }
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityProduct> getProductsByProductIds(List<Integer> productIds, Session session) 
    {
        Query<EntityProduct> query = session.getNamedQuery("getProductsByProductIds");
        query.setParameter("productIds", productIds);
        return query.getResultList();
    }
    
    public List<EntityProduct> getProductsByProductIds(List<Integer> productIds) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return getProductsByProductIds(productIds, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public EntityProduct createProduct(EntityProduct entityProduct, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityProduct.setCreatedOn(timeUtils.getCurrentTime());
        entityProduct.setModifiedOn(timeUtils.getCurrentTime());
        session.save(entityProduct);
        return entityProduct;
    }
    
    public EntityProduct createProduct(EntityProduct entityProduct)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return createProduct(entityProduct, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public boolean updateProduct(EntityProduct entityProduct, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityProduct.setModifiedOn(timeUtils.getCurrentTime());
        session.update(entityProduct);
        return true;
    }
    
    public boolean updateProduct(EntityProduct entityProduct)
    {
        Session session = HibernateUtil.getSession();        
        session.clear();
        Transaction tx = session.getTransaction(); 
        try 
        {
            tx.begin();
            updateProduct(entityProduct, session);
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
    
    public List<EntityProduct> getProducts(int offset, int limit) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityProduct> query = session.getNamedQuery("getAllProducts");
            query.setFirstResult(offset);
            query.setMaxResults(limit);

            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
    public int getTotalProducts() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityProduct> query = session.getNamedQuery("getAllProducts");
            return query.getResultList().size();
        } 
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityProduct> getUserProducts(int userId, int offset, int limit) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityProduct> query = session.getNamedQuery("getUserProducts");
            query.setParameter("userId", userId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);

            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
    
    public int getUserTotalProducts(int userId) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityProduct> query = session.getNamedQuery("getUserProducts");
            query.setParameter("userId", userId);            

            return query.getResultList().size();
        } 
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityProduct> getClosingProducts() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            TimeUtils timeUtils = new TimeUtils();
            long currentTime = timeUtils.getCurrentTime();
            Query<EntityProduct> query = session.getNamedQuery("getClosingProducts");
            query.setParameter("unixAuctionStart", currentTime);
            query.setParameter("unixAuctionEnd", currentTime);
            query.setFirstResult(0);
            query.setMaxResults(5);
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityProduct> searchProducts(int offset, int limit) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityProduct> query = session.getNamedQuery("getAllProducts");
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
    public int searchTotalProducts() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityProduct> query = session.getNamedQuery("getAllProducts");
            return query.getResultList().size();
        } 
        finally 
        {
            session.close();
        }
    }
}
