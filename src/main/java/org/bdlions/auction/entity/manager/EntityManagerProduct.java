package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.dto.DTOSearchParam;
import org.bdlions.auction.entity.EntityProduct;
import org.bdlions.auction.util.TimeUtils;
import org.bdlions.util.StringUtils;
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
    
    public List<EntityProduct> searchProduct(DTOSearchParam dtoSearchParam, int offset, int limit) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            String where = "";
            String strLimit = "";
            String strOffset = "";
            if(!StringUtils.isNullOrEmpty(dtoSearchParam.getReferenceId()))
            {
                String lowerReferenceId = dtoSearchParam.getReferenceId().toLowerCase();
                if(where.equals(""))
                {
                    where = " where lower(reference_id) like '%" + lowerReferenceId + "%'";
                }
                else 
                {
                    where += " and lower((reference_id) like '%" + lowerReferenceId + "%'";
                }
            }
            if(dtoSearchParam.getTypeId() > 0)
            {
                if(where.equals(""))
                {
                    where = " where type_id = " + dtoSearchParam.getTypeId();
                }
                else 
                {
                    where += " and type_id = " + dtoSearchParam.getTypeId();
                }
            }
            if(dtoSearchParam.getSizeId() > 0)
            {
                if(where.equals(""))
                {
                    where = " where size_id = " + dtoSearchParam.getSizeId();
                }
                else 
                {
                    where += " and size_id = " + dtoSearchParam.getSizeId();
                }
            }
            if(dtoSearchParam.getOccupationId() > 0)
            {
                if(where.equals(""))
                {
                    where = " where occupation_id = " + dtoSearchParam.getOccupationId();
                }
                else 
                {
                    where += " and occupation_id = " + dtoSearchParam.getOccupationId();
                }
            }
            if(dtoSearchParam.getPetId() > 0)
            {
                if(where.equals(""))
                {
                    where = " where pet_id = " + dtoSearchParam.getPetId();
                }
                else 
                {
                    where += " and pet_id = " + dtoSearchParam.getPetId();
                }
            }
            if(dtoSearchParam.getAvailabilityId() > 0)
            {
                String availabilityId = dtoSearchParam.getAvailabilityId()+"";
                if(where.equals(""))
                {
                    where = " where availability_ids like '%" + availabilityId + "%'";
                }
                else 
                {
                    where += " and availability_ids like '%" + availabilityId + "%'";
                }
            }
            if(dtoSearchParam.getMinPrice() > 0)
            {
                if(where.equals(""))
                {
                    where = " where base_price >= " + dtoSearchParam.getMinPrice();
                }
                else 
                {
                    where += " and base_price >= " + dtoSearchParam.getMinPrice();
                }
            }
            if(dtoSearchParam.getMaxPrice() > 0)
            {
                if(where.equals(""))
                {
                    where = " where base_price <= " + dtoSearchParam.getMaxPrice();
                }
                else 
                {
                    where += " and base_price <= " + dtoSearchParam.getMaxPrice();
                }
            }
            if(limit > 0)
            {
                strOffset = " offset " + offset;
                strLimit = " limit " + limit;
            }
            
            Query query = session.createSQLQuery("select {ep.*} from products ep " + where + strLimit + strOffset )
                    .addEntity("ep",EntityProduct.class);
            return query.list();            
        } 
        finally 
        {
            session.close();
        }
    }
    
    public int searchTotalProduct(DTOSearchParam dtoSearchParam) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            String where = "";
            if(!StringUtils.isNullOrEmpty(dtoSearchParam.getReferenceId()))
            {
                String lowerReferenceId = dtoSearchParam.getReferenceId().toLowerCase();
                if(where.equals(""))
                {
                    where = " where lower(reference_id) like '%" + lowerReferenceId + "%'";
                }
                else 
                {
                    where += " and lower((reference_id) like '%" + lowerReferenceId + "%'";
                }
            }  
            if(dtoSearchParam.getTypeId() > 0)
            {
                if(where.equals(""))
                {
                    where = " where type_id = " + dtoSearchParam.getTypeId();
                }
                else 
                {
                    where += " and type_id = " + dtoSearchParam.getTypeId();
                }
            }
            if(dtoSearchParam.getSizeId() > 0)
            {
                if(where.equals(""))
                {
                    where = " where size_id = " + dtoSearchParam.getSizeId();
                }
                else 
                {
                    where += " and size_id = " + dtoSearchParam.getSizeId();
                }
            }
            if(dtoSearchParam.getOccupationId() > 0)
            {
                if(where.equals(""))
                {
                    where = " where occupation_id = " + dtoSearchParam.getOccupationId();
                }
                else 
                {
                    where += " and occupation_id = " + dtoSearchParam.getOccupationId();
                }
            }
            if(dtoSearchParam.getPetId() > 0)
            {
                if(where.equals(""))
                {
                    where = " where pet_id = " + dtoSearchParam.getPetId();
                }
                else 
                {
                    where += " and pet_id = " + dtoSearchParam.getPetId();
                }
            }
            if(dtoSearchParam.getAvailabilityId() > 0)
            {
                String availabilityId = dtoSearchParam.getAvailabilityId()+"";
                if(where.equals(""))
                {
                    where = " where availability_ids like '%" + availabilityId + "%'";
                }
                else 
                {
                    where += " and availability_ids like '%" + availabilityId + "%'";
                }
            }
            if(dtoSearchParam.getMinPrice() > 0)
            {
                if(where.equals(""))
                {
                    where = " where base_price >= " + dtoSearchParam.getMinPrice();
                }
                else 
                {
                    where += " and base_price >= " + dtoSearchParam.getMinPrice();
                }
            }
            if(dtoSearchParam.getMaxPrice() > 0)
            {
                if(where.equals(""))
                {
                    where = " where base_price <= " + dtoSearchParam.getMaxPrice();
                }
                else 
                {
                    where += " and base_price <= " + dtoSearchParam.getMaxPrice();
                }
            }
            
            Query query = session.createSQLQuery("select {ep.*} from products ep " + where)
                    .addEntity("ep",EntityProduct.class);
            return query.list().size();            
        } 
        finally 
        {
            session.close();
        }
    }
}
