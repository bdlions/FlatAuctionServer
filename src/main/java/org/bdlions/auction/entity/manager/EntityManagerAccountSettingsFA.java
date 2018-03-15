package org.bdlions.auction.entity.manager;

import java.util.List;
import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityAccountSettingsFA;
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
public class EntityManagerAccountSettingsFA {
    private final Logger logger = LoggerFactory.getLogger(EntityManagerAccountSettingsFA.class);
    
    public EntityAccountSettingsFA getAccountSettingsFAByUserId(int userId)
    {
        Session session = HibernateUtil.getSession();
        try 
        {            
            Query<EntityAccountSettingsFA> query = session.getNamedQuery("getAccountSettingsFAByUserId");
            query.setParameter("userId", userId);
            List<EntityAccountSettingsFA> accountSettingsFAList = query.getResultList();
            if(accountSettingsFAList == null || accountSettingsFAList.isEmpty())
            {
                return null;
            }
            else
            {
                return accountSettingsFAList.get(0);
            }           
        } 
        finally 
        {
            session.close();
        }
    }
    
    public EntityAccountSettingsFA createAccountSettingsFA(EntityAccountSettingsFA entityAccountSettingsFA, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityAccountSettingsFA.setCreatedOn(timeUtils.getCurrentTime());
        entityAccountSettingsFA.setModifiedOn(timeUtils.getCurrentTime());
        session.save(entityAccountSettingsFA);
        return entityAccountSettingsFA;
    }
    
    public EntityAccountSettingsFA createAccountSettingsFA(EntityAccountSettingsFA entityAccountSettingsFA)
    {
        Session session = HibernateUtil.getSession();
        try 
        {
            return createAccountSettingsFA(entityAccountSettingsFA, session);
        } 
        finally 
        {
            session.close();
        }
    }
    
    public boolean updateAccountSettingsFA(EntityAccountSettingsFA entityAccountSettingsFA, Session session)
    {
        TimeUtils timeUtils = new TimeUtils();
        entityAccountSettingsFA.setModifiedOn(timeUtils.getCurrentTime());
        session.update(entityAccountSettingsFA);
        return true;
    }
    
    public boolean updateAccountSettingsFA(EntityAccountSettingsFA entityAccountSettingsFA)
    {
        Session session = HibernateUtil.getSession();        
        session.clear();
        Transaction tx = session.getTransaction(); 
        try 
        {
            tx.begin();
            updateAccountSettingsFA(entityAccountSettingsFA, session);
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
}
