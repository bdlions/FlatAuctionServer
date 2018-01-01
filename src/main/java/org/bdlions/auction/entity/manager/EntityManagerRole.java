package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityRole;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Nazmul Hasan
 */
public class EntityManagerRole 
{
    public List<EntityRole> getAllRoles()
    {
        Session session = HibernateUtil.getSession();
        try
        {
            return getAllRoles(session);
        }
        finally
        {
            session.close();
        }
    }
    
    public List<EntityRole> getAllRoles(Session session)
    {
        Query<EntityRole> query = session.getNamedQuery("getAllRoles");
        return query.getResultList();
    }
    
    public EntityRole getRoleByRoleId(int roleId)
    {
        Session session = HibernateUtil.getSession();
        try
        {
            return getRoleByRoleId(session, roleId);
        }
        finally
        {
            session.close();
        }
    }
    public EntityRole getRoleByRoleId(Session session, int roleId)
    {
        Query<EntityRole> query = session.getNamedQuery("getRoleByRoleId");
        query.setParameter("roleId", roleId);
        return query.uniqueResult();
    }
    
    public List<EntityRole> getRolesByRoleIds(List<Integer> roleIds) 
    {
        if(roleIds == null || roleIds.isEmpty())
        {
            return null;
        }
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityRole> query = session.getNamedQuery("getRolesByRoleIds");
            query.setParameter("roleIds", roleIds);
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
