package org.bdlions;

import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.entity.EntityGender;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nazmul hasan
 */
public class KeepAliveDBManager implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(KeepAliveDBManager.class.getName());
    private Thread t;
    private String threadName;
    long sleepTime = 7200 * 1000;
    public KeepAliveDBManager(String threadName)
    {
        this.threadName = threadName;        
    }
    
    @Override
    public void run() 
    {
        while(true)
        {
            try
            {
                System.out.println("Keep Alive DB Manager executes.");
                Session session = HibernateUtil.getSession();
                try
                {
                    Query<EntityGender> query = session.getNamedQuery("getAllGenders");
                    query.getResultList();
                }
                finally
                {
                    session.close();
                }
                Thread.sleep(sleepTime);
            }
            catch (Exception ex) {
                logger.error(ex.toString());
            } finally {

            }            
        }
    }
        
    
    public void start ()
    {
        if (t == null)
        {
           t = new Thread (this, threadName);
           t.start ();
        }
    }
}
