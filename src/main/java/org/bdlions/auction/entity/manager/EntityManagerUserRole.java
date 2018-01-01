package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityUserRole;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerUserRole 
{
    public EntityUserRole createUserRole(EntityUserRole entityUserRole, Session session)
    {
        session.save(entityUserRole);
        return entityUserRole;
    }
    
    public EntityUserRole createUserRole(EntityUserRole entityUserRole)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return createUserRole(entityUserRole, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityUserRole> getUserRolesByUserId(int userId, Session session)
    {
        Query<EntityUserRole> query = session.getNamedQuery("getUserRolesByUserId");   
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    public List<EntityUserRole> getUserRolesByUserId(int userId)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            return getUserRolesByUserId(userId, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public int deleteUserRolesByUserId(int userId, Session session)
    {
        if(userId > 0)
        {
            Query<EntityUserRole> query = session.getNamedQuery("deleteUserRolesByUserId");   
            query.setParameter("userId", userId);
            return query.executeUpdate();
        }
        return 0;
    }
    
    public int deleteUserRolesByUserId(int userId)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            return deleteUserRolesByUserId(userId, session);
        } 
        finally 
        {
            session.close();
        }
    }
}
