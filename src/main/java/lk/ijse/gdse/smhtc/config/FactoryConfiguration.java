package lk.ijse.gdse.smhtc.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;  // to create sessions

    private FactoryConfiguration(){
        Configuration configure = new Configuration().configure();
//        configure.addAnnotatedClass(Customer.class);
//        configure.addAnnotatedClass(Order.class);
        sessionFactory = configure.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance(){
//        if(factoryConfiguration == null) {
//            factoryConfiguration = new FactoryConfiguration();
//        }
//        return factoryConfiguration;

        return factoryConfiguration == null ?
                factoryConfiguration = new FactoryConfiguration() :
                factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
