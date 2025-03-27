package lk.ijse.gdse.smhtc.config;

import lk.ijse.gdse.smhtc.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

//////////////////////////////// Hibernate.cfg.xml ////////////////////////////////////////////////////

//public class FactoryConfiguration {
//    private static FactoryConfiguration factoryConfiguration;
//    private SessionFactory sessionFactory;  // to create sessions
//
//    private FactoryConfiguration(){
//        Configuration configure = new Configuration().configure();
////        configure.addAnnotatedClass(Customer.class);
////        configure.addAnnotatedClass(Order.class);
//        sessionFactory = configure.buildSessionFactory();
//    }
//
//    public static FactoryConfiguration getInstance(){
////        if(factoryConfiguration == null) {
////            factoryConfiguration = new FactoryConfiguration();
////        }
////        return factoryConfiguration;
//
//        return factoryConfiguration == null ?
//                factoryConfiguration = new FactoryConfiguration() :
//                factoryConfiguration;
//    }
//
//    public Session getSession() {
//        return sessionFactory.openSession();
//    }
//}

//////////////////////////// Hibernate Property files //////////////////////////////////////////////
public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private static SessionFactory sessionFactory;
    private FactoryConfiguration(){
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        configuration.setProperties(properties);

        configuration.addAnnotatedClass(User.class)
            .addAnnotatedClass(Patient.class)
            .addAnnotatedClass(PatientProgram.class)
            .addAnnotatedClass(Payment.class)
            .addAnnotatedClass(Therapist.class)
            .addAnnotatedClass(TherapistAvailability.class)
            .addAnnotatedClass(TherapistProgram.class)
            .addAnnotatedClass(TherapyProgram.class)
            .addAnnotatedClass(TherapySession.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ?
                (factoryConfiguration = new FactoryConfiguration()) :
                factoryConfiguration;
    }


    public Session getSession() {
        return sessionFactory.openSession();
    }

}