package peligrosotrader.predict.persistence;

import java.io.File;
import java.io.FileOutputStream;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

import peligrosotrader.predict.pm.PM;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

    static {
        try {
        	
        	
        	//File f = new File("C:/Documents and Settings/Peligroso/workspace/peligrosotrader.predict/hibernate.cfg.xml");

            // Create the SessionFactory from hibernate.cfg.xml
        	//sessionFactory = new Configuration().configure(f).buildSessionFactory();
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void savePM(PM inPM) {
    	
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.beginTransaction();

        session.save(inPM);

        session.getTransaction().commit();

    }
    
    public static PM loadPM(){
    	
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.beginTransaction();

        PM loaded = (PM)session.load(peligrosotrader.predict.pm.PM.class, false);

        session.getTransaction().commit();
        
        return loaded;
    }

}
