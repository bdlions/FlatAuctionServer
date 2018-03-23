package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.entity.EntityUserRole;
import org.bdlions.auction.util.StringUtils;
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
public class EntityManagerUser 
{
    private final Logger logger = LoggerFactory.getLogger(EntityManagerUser.class);
    /**
     * This method will return entity user by email
     * @param email email
     * @return EntityUser EntityUser
     */
    public EntityUser getUserByEmail(String email)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityUser> query = session.getNamedQuery("getUserByEmail");
            query.setParameter("email", email);
            List<EntityUser> userList = query.getResultList();
            if(userList == null || userList.isEmpty())
            {
                return null;
            }
            else
            {
                return userList.get(0);
            }           
        } 
        finally 
        {
            session.close();
        }
    }
    
    public EntityUser getUserByFBCode(String fbCode)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityUser> query = session.getNamedQuery("getUserByFBCode");
            query.setParameter("fbCode", fbCode);
            List<EntityUser> userList = query.getResultList();
            if(userList == null || userList.isEmpty())
            {
                return null;
            }
            else
            {
                return userList.get(0);
            }           
        } 
        finally 
        {
            session.close();
        }
    }
    
    public EntityUser getUserByEmailVerificationCode(String emailVerificationCode)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityUser> query = session.getNamedQuery("getUserByEmailVerificationCode");
            query.setParameter("emailVerificationCode", emailVerificationCode);
            List<EntityUser> userList = query.getResultList();
            if(userList == null || userList.isEmpty())
            {
                return null;
            }
            else
            {
                return userList.get(0);
            }           
        } 
        finally 
        {
            session.close();
        }
    }
    
    /**
     * This method will return entity user by user id
     * @param userId user id
     * @return EntityUser EntityUser
     */
    public EntityUser getUserByUserId(int userId)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityUser> query = session.getNamedQuery("getUserByUserId");
            query.setParameter("userId", userId);
            List<EntityUser> userList = query.getResultList();
            if(userList == null || userList.isEmpty())
            {
                return null;
            }
            else
            {
                return userList.get(0);
            }                   
        } 
        finally 
        {
            session.close();
        }
    }
    
    /**
     * This method will return entity user by identity and password
     * @param identity email
     * @param password password
     * @return EntityUser EntityUser
     */
    public EntityUser getUserByCredential(String identity, String password) {
        if(StringUtils.isNullOrEmpty(password))
        {
            return null;
        }        
        EntityUser selectedUser = getUserByEmail(identity);
        if (selectedUser == null) {
            return selectedUser;
        }

        if (selectedUser.getPassword().equals(password)) {
            return selectedUser;
        }
        return null;
    }
    
    /**
     * This method will create entity user using session
     * @param entityUser entity user
     * @param session session
     * @return EntityUser EntityUser
     */
    public EntityUser createUser(EntityUser entityUser, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityUser.setCreatedOn(timeUtils.getCurrentTime());
        entityUser.setModifiedOn(timeUtils.getCurrentTime());
        session.save(entityUser);
        return entityUser;
    }
    
    /**
     * This method will create entity user
     * @param entityUser entity user
     * @return EntityUser EntityUser
     */
    public EntityUser createUser(EntityUser entityUser)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return createUser(entityUser, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    /**
     * This method will create entity user and/or entity user role using session
     * @param entityUser entity user
     * @param entityUserRole entity user role
     * @param session session
     * @return EntityUser EntityUser
     */
    public EntityUser createUser(EntityUser entityUser, EntityUserRole entityUserRole, Session session)
    {
        entityUser = createUser(entityUser, session);
        if(entityUser != null && entityUserRole != null)
        {
            entityUserRole.setUserId(entityUser.getId());
            EntityManagerUserRole entityManagerUserRole = new EntityManagerUserRole();
            entityManagerUserRole.createUserRole(entityUserRole, session);
        }
        return entityUser;
    }
    
    /**
     * This method will create entity user and/or entity user role
     * @param entityUser entity user
     * @param entityUserRole entity user role
     * @return EntityUser EntityUser
     */
    public EntityUser createUser(EntityUser entityUser, EntityUserRole entityUserRole)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return createUser(entityUser, entityUserRole, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public EntityUser createUser(EntityUser entityUser, List<EntityUserRole> entityUserRoles, Session session)
    {
        entityUser = createUser(entityUser, session);
        if(entityUser != null && entityUserRoles != null && !entityUserRoles.isEmpty())
        {
            for(EntityUserRole entityUserRole: entityUserRoles)
            {
                entityUserRole.setUserId(entityUser.getId());
                EntityManagerUserRole entityManagerUserRole = new EntityManagerUserRole();
                entityManagerUserRole.createUserRole(entityUserRole, session);
            }            
        }
        return entityUser;
    }
    
    public EntityUser createUser(EntityUser entityUser, List<EntityUserRole> entityUserRoles)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return createUser(entityUser, entityUserRoles, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    /**
     * This method will update entity user using session
     * @param entityUser entity user
     * @param session session
     * @return boolean true
     */
    public boolean updateUser(EntityUser entityUser, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityUser.setModifiedOn(timeUtils.getCurrentTime());
        session.update(entityUser);
        return true;
    }
    
    /**
     * This method will update entity user
     * @param entityUser entity user
     * @return boolean true
     */
    public boolean updateUser(EntityUser entityUser)
    {
        Session session = HibernateUtil.getSession();        
        session.clear();
        Transaction tx = session.getTransaction(); 
        try 
        {
            tx.begin();
            updateUser(entityUser, session);
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
    
    public boolean updateUser(EntityUser entityUser, List<EntityUserRole> entityUserRoles, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityUser.setModifiedOn(timeUtils.getCurrentTime());
        session.update(entityUser);
        if(entityUser.getId() > 0 && entityUserRoles != null && !entityUserRoles.isEmpty())
        {
            //delete current roles
            EntityManagerUserRole entityManagerUserRole = new EntityManagerUserRole();
            entityManagerUserRole.deleteUserRolesByUserId(entityUser.getId(), session);
            //add new roles
            for(EntityUserRole entityUserRole: entityUserRoles)
            {
                entityUserRole.setUserId(entityUser.getId());
                entityManagerUserRole.createUserRole(entityUserRole, session);
            } 
        }
        return true;
    }
    public boolean updateUser(EntityUser entityUser, List<EntityUserRole> entityUserRoles)
    {
        Session session = HibernateUtil.getSession();   
        Transaction tx = session.getTransaction(); 
        try 
        {            
            tx.begin();
            updateUser(entityUser, entityUserRoles, session);
            tx.commit();
            return true;
        } 
        catch(Exception ex)
        {
            logger.debug(ex.toString());
            tx.rollback();
            return false;
        }
        finally 
        {            
            session.close();
        }
    }
    /**
     * This method will return user list
     * @param offset offset
     * @param limit limit
     * @return List entity user list
     */
    public List<EntityUser> getUsers(int offset, int limit) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityUser> query = session.getNamedQuery("getUsers");
            query.setFirstResult(offset);
            query.setMaxResults(limit);

            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
    public int getTotalUsers() 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityUser> query = session.getNamedQuery("getUsers");
            return query.getResultList().size();
        } 
        finally 
        {
            session.close();
        }
    }
    
    public List<EntityUser> getUsersByUserIds(List<Integer> userIds) 
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            Query<EntityUser> query = session.getNamedQuery("getUsersByUserIds");
            query.setParameter("userIds", userIds);
            
            return query.getResultList();
        } 
        finally 
        {
            session.close();
        }
    }
}
